package com.bolin.group1.elasticsearch.dir1;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.math.BigDecimal;

public class Try1 {


    public static void main(String[] args) throws IOException {
        // URL and API key
        String serverUrl = "https://localhost:9200";
        String apiKey = "VldiUHk1UUJaVEVTY3IzcURTbnc6M0h4QzZ6dmhSMmlsQ0c2NFY3Q05CUQ==";
        // Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

//        esClient.indices().create(c -> c
//                .index("products")
//        );
        Product product = new Product("bk-2", "City bike", new BigDecimal("123.0"));

        IndexResponse response = esClient.index(i -> i
                .index("products")
                .id(product.getSku())
                .document(product)
        );

        System.out.println("Indexed with version " + response.version());

        String searchText = "City bike";

        SearchResponse<Product> response2 = esClient.search(s -> s
                        .index("products")
                        .query(q -> q
                                .match(t -> t
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                Product.class
        );


// Use the client...

// Close the client, also closing the underlying transport object and network connections..

        esClient.close();

    }


}
