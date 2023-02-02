package io.pw.response;

import io.pw.db.ProductRepository;
import io.pw.db.Repository;
import io.pw.db.entity.Product;
import io.pw.exeption.ProductQtyException;
import io.pw.util.QueryParamParser;
import io.pw.util.UriParamParser;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by pwykowski
 */
public class ReduceHandler implements ResponseHandler {

	private final URI uri;
	private final Repository<Product> repository = new ProductRepository();
	private final QueryParamParser queryParamParser = new QueryParamParser();

	public ReduceHandler(URI uri) {
		this.uri = uri;
	}

	@Override
	public byte[] handle() {
		long productID = -1L;
		List<UriParamParser> params = queryParamParser.parse(uri);


		for (UriParamParser param : params) {
			if (param.getParam().equals("id")) {
				productID = Long.parseLong(param.getValue());
			}
		}

		if (productID == -1) {
			return new byte[0];
		}

		Optional<Product> product = repository.findById(productID);
		if (product.isEmpty()) {
			return new byte[0];
		}

		try {
			product.get().decreaseQty();
		} catch (ProductQtyException e) {
			return new byte[0];
		}

		repository.store(product.get());
		return new byte[0];
	}
}
