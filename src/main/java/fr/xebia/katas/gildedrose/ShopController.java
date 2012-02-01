package fr.xebia.katas.gildedrose;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.simpleframework.http.Response;
import org.stringtemplate.v4.ST;
import com.google.inject.Inject;

public class ShopController {
	private final Inn inn;

	@Inject
	public ShopController(Inn inn) {
		this.inn = inn;
		System.out.println("instanciate");
	}

	public void render(Response resp) throws IOException {
		inn.updateQuality();
		String html = FileUtils.readFileToString(new File("index.html"));
		ST template = new ST(html, '$', '$');

		template.add("nbItems", inn.getItems().size());
		template.add("items", inn.getItems());

		resp.getPrintStream().append(template.render());
	}
}
