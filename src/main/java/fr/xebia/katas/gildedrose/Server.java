package fr.xebia.katas.gildedrose;

import static com.google.common.collect.Iterables.*;
import static com.google.inject.Guice.*;
import static com.google.inject.name.Names.*;
import static java.util.Arrays.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.AbstractService;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.name.Named;

public class Server extends AbstractService implements Container {
	private final int port;
	private final Injector injector;
	private SocketConnection socketConnection;

	public Server(int port) {
		this.port = port;
		injector = createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Object.class, named("/")).toInstance(new ShopController(Inn.initInn()));
			}

			protected <T> LinkedBindingBuilder<T> bind(Class<T> key, Named name) {
				return bind(Key.get(key, name));
			}
		});
	}

	@Override
	public void handle(Request req, Response resp) {
		List<String> path = asList(req.getPath().getSegments());
		String action = getFirst(path, "/");

		try {
			invoke(controller(action), "render", arguments(resp, path));
		} finally {
			try {
				resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Object> arguments(Response resp, List<String> path) {
		return ImmutableList.builder().add(resp).addAll(Iterables.skip(path, 1)).build();
	}

	private Object controller(String action) {
		return injector.getInstance(Key.get(Object.class, named(action)));
	}

	public static void main(String[] args) {
		new Server(8080).startAndWait();
	}

	@Override
	protected void doStart() {
		try {
			socketConnection = new SocketConnection(this);
			socketConnection.connect(new InetSocketAddress(port));
			notifyStarted();
		} catch (IOException e) {
			notifyFailed(e);
		}
	}

	@Override
	protected void doStop() {
		try {
			socketConnection.close();
			notifyStopped();
		} catch (IOException e) {
			notifyFailed(e);
		}
	}

	public static void invoke(Object target, String methodName, List<Object> arguments) {
		try {
			for (Method method : target.getClass().getMethods()) {
				if (!method.getName().equals(methodName)) {
					continue;
				}

				Object[] convertedArguments = new Object[arguments.size()];

				int i = 0;
				for (Object argument : arguments) {
					Object convertedArgument = argument;
					if (method.getParameterTypes()[i] == int.class) {
						convertedArgument = Integer.parseInt((String) argument);
					}
					convertedArguments[i++] = convertedArgument;
				}

				method.invoke(target, convertedArguments);
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
}
