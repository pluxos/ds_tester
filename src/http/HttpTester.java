package http;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpTester {
	private CloseableHttpClient httpclient;
	private String baseUrl;
	private long myId;

	static int testLen = 2;

	public HttpTester(String url, long id) {
		httpclient = HttpClients.createDefault();
		baseUrl = url;
		myId = id;
	}

	enum Operation {
		successPost, failPost, sucessGet, failGet, successPut, failPut, successDelete, failtDelete, successHead, failHead,
	}

	public final static void main(String[] args) throws Exception {
		for (Operation op : Operation.values()) {
			boolean result = runTest(args[0], args[1], true, op);

			if (result)
				System.out.println(op + ": passed!");
			else
				System.out.println(op + ": failed!");

		}
	}

	private static boolean runTest(String ip, String port, boolean expectedResult, Operation requestType)
			throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(testLen);
		Set<Callable<Boolean>> callables = new HashSet<Callable<Boolean>>();

		for (long id = 0; id < 1 * testLen; id++) {
			final HttpTester tester = new HttpTester(ip + ":" + port + "/", id);
			callables.add(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					switch (requestType) {
					case successPost:
						return tester.successPost(tester.myId);
					case sucessGet:
						return tester.successGet(tester.myId);
					case successDelete:
						return tester.successDelete(tester.myId);
					case successHead:
						return tester.successHead(tester.myId);
					case successPut:
						return tester.successPut(tester.myId);
					case failPost:
						return tester.failPost(tester.myId);
					case failGet:
						return tester.failGet(tester.myId);
					case failHead:
						return tester.failHead(tester.myId);
					case failPut:
						return tester.failPut(tester.myId);
					case failtDelete:
						return tester.failDelete(tester.myId);

					default:
						return false;
					}
				}
			});
		}

		List<Future<Boolean>> futures = executor.invokeAll(callables);
		boolean result = true;
		for (Future<Boolean> f : futures) {
			result = result && f.get();

			if (!result)
				break;
		}
		return result;
	}

	protected Boolean failHead(long myId2) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Boolean successHead(long myId2) {
		// TODO Auto-generated method stub
		return null;
	}

	private void close() throws IOException {
		httpclient.close();
	}

	private boolean successPost(long id) throws Exception {
		boolean res = false;
		// Success POST
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(id)).build();
		HttpPost postMethod = new HttpPost(uri);
		postMethod.setEntity(new StringEntity(Long.toHexString(id)));
		CloseableHttpResponse postResponse = httpclient.execute(postMethod);

		try {
			// System.out.println(postResponse.getStatusLine());
			int statusCode = postResponse.getStatusLine().getStatusCode();
			if (statusCode == 200 || statusCode == 201 || statusCode == 202) {
				res = true;
			} else {
				res = false;
			}

			HttpEntity postEntity = postResponse.getEntity();
			EntityUtils.consume(postEntity);
		} finally {
			postResponse.close();
			close();
		}
		return res;
	}

	private boolean failPost(long myId) throws Exception {
		boolean res = false;
		// Fail POST
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();
		HttpPost postMethod = new HttpPost(uri);
		postMethod.setEntity(new StringEntity("data=" + Long.toHexString(myId)));
		CloseableHttpResponse postResponse = httpclient.execute(postMethod);

		try {
			int statusCode = postResponse.getStatusLine().getStatusCode();

			if (statusCode == 400 || statusCode == 403 || statusCode == 409) {
				res = true;
			} else {
				System.out.println("code " + statusCode + "  " + myId);
				res = false;
			}

			HttpEntity postEntity = postResponse.getEntity();
			EntityUtils.consume(postEntity);
		} finally {
			postResponse.close();
		}
		return res;
	}

	private boolean successGet(long myId) throws Exception {
		boolean res = false;

		// Success GET
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();
		HttpGet getMethod = new HttpGet(uri);
		CloseableHttpResponse getResponse = httpclient.execute(getMethod);
		try {
			// System.out.println(getResponse.getStatusLine());
			HttpEntity getEntity = getResponse.getEntity();
			int statusCode = getResponse.getStatusLine().getStatusCode();
			String entityString = EntityUtils.toString(getEntity);
			if ((statusCode == 200 || statusCode == 302) && entityString.equals(Long.toHexString(myId))) {
				res = true;
			} else {
				System.out.println(entityString);
				res = false;
			}
			EntityUtils.consume(getEntity);
		} finally {
			getResponse.close();
		}

		return res;
	}

	private boolean failGet(long myId) throws Exception {
		boolean res = false;
		// Fail GET
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpGet getMethod = new HttpGet(uri);
		CloseableHttpResponse getResponse = httpclient.execute(getMethod);
		try {
			// System.out.println(getResponse.getStatusLine());
			HttpEntity getEntity = getResponse.getEntity();
			int statusCode = getResponse.getStatusLine().getStatusCode();
			String entityString = EntityUtils.toString(getEntity);
			if (statusCode == 404 || statusCode == 400) {
				res = true;
			} else {
				res = false;
			}
			EntityUtils.consume(getEntity);
		} finally {
			getResponse.close();
		}

		return res;
	}

	private boolean successPut(long myId) throws Exception {
		boolean res = false;
		// Success PUT
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpPut putMethod = new HttpPut(uri);
		putMethod.setEntity(new StringEntity("data=" + Long.toHexString(-1 * myId)));
		CloseableHttpResponse postResponse = httpclient.execute(putMethod);

		try {
			// System.out.println(postResponse.getStatusLine());
			if (postResponse.getStatusLine().getStatusCode() == 200
					|| postResponse.getStatusLine().getStatusCode() == 204) {
				res = true;
			} else {
				res = false;
			}

			HttpEntity postEntity = postResponse.getEntity();
			EntityUtils.consume(postEntity);
		} finally {
			postResponse.close();
		}

		return res;
	}

	private boolean failPut(long myId) throws Exception {
		boolean res = false;
		// Fail PUT
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpPut putMethod = new HttpPut(uri);
		putMethod.setEntity(new StringEntity("data=" + Long.toHexString(myId)));
		CloseableHttpResponse postResponse = httpclient.execute(putMethod);

		try {
			// System.out.println(postResponse.getStatusLine());
			if (postResponse.getStatusLine().getStatusCode() == 404) {
				res = true;
			} else {
				res = false;
			}

			HttpEntity postEntity = postResponse.getEntity();
			EntityUtils.consume(postEntity);
		} finally {
			postResponse.close();
		}

		return res;
	}

	private boolean successDelete(long myId) throws Exception {
		boolean res = false;
		// Success DELETE
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpDelete delMethod = new HttpDelete(uri);
		CloseableHttpResponse getResponse = httpclient.execute(delMethod);
		try {
			// System.out.println(getResponse.getStatusLine());
			HttpEntity getEntity = getResponse.getEntity();
			if (getResponse.getStatusLine().getStatusCode() == 200 || getResponse.getStatusLine().getStatusCode() == 202
					|| getResponse.getStatusLine().getStatusCode() == 204) {
				res = true;
			} else {
				res = false;
			}
			EntityUtils.consume(getEntity);
		} finally {
			getResponse.close();
		}

		return res;
	}

	private boolean failDelete(long myId) throws Exception {
		boolean res = false;
		// Fail DELETE
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpDelete delMethod = new HttpDelete(uri);
		CloseableHttpResponse getResponse = httpclient.execute(delMethod);
		try {
			// System.out.println(getResponse.getStatusLine());
			HttpEntity getEntity = getResponse.getEntity();
			if (getResponse.getStatusLine().getStatusCode() == 404) {
				res = true;
			} else {
				res = false;
			}
			EntityUtils.consume(getEntity);
		} finally {
			getResponse.close();
		}

		return res;
	}

	private boolean successLongPostGet(long myId) throws Exception {
		boolean res = false;
		String longString = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
				+ "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
		// Success POST
		URI uri = new URIBuilder().setScheme("http").setHost(baseUrl).setPath(Long.toString(myId)).build();

		HttpPost postMethod = new HttpPost(uri);
		postMethod.setEntity(new StringEntity("data=" + longString));
		CloseableHttpResponse postResponse = httpclient.execute(postMethod);

		try {
			// System.out.println(postResponse.getStatusLine());
			int statusCode = postResponse.getStatusLine().getStatusCode();
			if (statusCode == 200 || statusCode == 201) {
				res = true;
			} else {
				res = false;
			}

			HttpEntity postEntity = postResponse.getEntity();
			EntityUtils.consume(postEntity);
		} finally {
			postResponse.close();
		}

		if (res) {
			// Success GET
			HttpGet getMethod = new HttpGet(uri);
			CloseableHttpResponse getResponse = httpclient.execute(getMethod);
			try {
				HttpEntity getEntity = getResponse.getEntity();
				int statusCode = getResponse.getStatusLine().getStatusCode();
				String entityString = EntityUtils.toString(getEntity);
				if ((statusCode == 200 || statusCode == 302) && entityString.equals(longString)) {
					res = true;
				} else {
					System.out.println(entityString);
					res = false;
				}
				EntityUtils.consume(getEntity);
			} finally {
				getResponse.close();
			}
		}
		return res;
	}
}
