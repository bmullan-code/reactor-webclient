package hello;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

public class GreetingWebClient {
	
	
	private WebClient client_f = WebClient.create("https://trace-frontend-brave-hedgehog.cfapps.io");
	private WebClient client_i = WebClient.create("https://trace-intermediate-bright-numbat.cfapps.io");
	private WebClient client_b = WebClient.create("https://trace-backend-grateful-mandrill.cfapps.io");
		
//	private String[] apis = new String[] {"https://trace-frontend-brave-hedgehog.cfapps.io","https://trace-intermediate-bright-numbat.cfapps.io","https://trace-backend-grateful-mandrill.cfapps.io"};
//	private WebClient[] clients = null;

	private Mono<ClientResponse> result_f = client_f.get()
			.uri("/api")
			.accept(MediaType.TEXT_PLAIN)
			.exchange();

	private Mono<ClientResponse> result_i = client_i.get()
			.uri("/api")
			.accept(MediaType.TEXT_PLAIN)
			.exchange();

	private Mono<ClientResponse> result_b = client_b.get()
			.uri("/api")
			.accept(MediaType.TEXT_PLAIN)
			.exchange();

	
	public String getResult() {
		
//		result_f.subscribeOn(Schedulers.parallel());
//		result_i.subscribeOn(Schedulers.parallel());
//		result_b.subscribeOn(Schedulers.parallel());

		// return ">> result = " + result_f.flatMap(res -> res.bodyToMono(String.class)).block();
//		Mono.zip(result_f, result_i, result_b, 
//				(a,b,c) -> a.flatMap(res -> res.bodyToMono(String.class)));
		
		
		
//		for (int i = 0 ; i < apis.length; i++) {
//			clients[i] = WebClient.create(apis[i]);
//		}
		
//		String foo = "foo:" + Mono.zip(result_f,result_i,result_b,
//				( res1,res2,res3) -> res1.bodyToMono(String.class).block());
//			.flatMap(tuple3 -> tuple3.getT1().bodyToMono(String.class).block());
//			.map(tuple3 -> tuple3.getT1().concat(tuple3.getT2()).concat(tuple3.getT3()))
//			.map(String::toUpperCase)
//			.subscribe(res -> res.bodyToMono(String.class));

		//		result_i.flatMap(res -> res.bodyToMono(String.class)).block();
		
//		Flux.concat(result_f,result_i,result_b)
//			.subscribe(res -> this.process(res));
		
		Mono<String> all = Mono.zip(result_f, result_i, result_b).flatMap(a -> this.process(a));
		// we subscribe and then wait for all to be done
		String s = all.block();
		
		System.out.println(">> results:"+s);
		
		// return ">> result = " + result_f.flatMap(res -> res.bodyToMono(String.class)).block();
		return ""+all;
	}
	
	private Mono<String> process( Tuple3<ClientResponse, ClientResponse, ClientResponse> res ) {
		Mono<String> s1 = res.getT1().bodyToMono(String.class);
		Mono<String> s2 = res.getT2().bodyToMono(String.class);
		Mono<String> s3 = res.getT3().bodyToMono(String.class);
		return s1;
	}
}
