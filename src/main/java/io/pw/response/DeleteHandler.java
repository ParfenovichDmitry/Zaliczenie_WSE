package io.pw.response;

import io.pw.db.ProductRepository;
import io.pw.db.Repository;
import io.pw.db.entity.Product;
import io.pw.util.QueryParamParser;
import io.pw.util.UriParamParser;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class DeleteHandler implements ResponseHandler{

    private final URI uri;
    private final Repository<Product> repository = new ProductRepository();
    private final QueryParamParser queryParamParser = new QueryParamParser();


    public DeleteHandler(URI requestURI) {
        this.uri = requestURI;
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

        repository.delete(product.get());
        return new byte[0];
    }
}
