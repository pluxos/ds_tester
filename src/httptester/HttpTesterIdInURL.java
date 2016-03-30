package httptester;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class HttpTesterIdInURL {
    private CloseableHttpClient httpclient;
	private String baseUrl;

    public HttpTesterIdInURL(String url) {
        httpclient = HttpClients.createDefault();
        baseUrl = url;
	}

	public final static void main(String[] args) throws Exception {
    	HttpTesterIdInURL tester = new HttpTesterIdInURL("http://"+ args[0] + ":" + args[1] + "/");
    	
        try 
        {
        	if(tester.successPost())
            	System.out.println("Success POST: passed!");
        	else
        		System.err.println("Success POST: failed!");

        	if(tester.failPost())
            	System.out.println("Fail POST: passed!");
        	else
        		System.err.println("Fail POST: failed!");

        	
        	if(tester.successGet())
            	System.out.println("Success GET: passed!");
        	else
        		System.err.println("Success GET: failed!");

        	if(tester.failGet())
            	System.out.println("Fail GET: passed!");
        	else
        		System.err.println("Fail GET: failed!");
        	
        	if(tester.successPut())
            	System.out.println("Success PUT: passed!");
        	else
        		System.err.println("Success PUT: failed!");

        	if(tester.failPut())
            	System.out.println("Fail PUT: passed!");
        	else
        		System.err.println("Fail PUT: failed!");
        	
        	if(tester.successDelete())
            	System.out.println("Success DELETE: passed!");
        	else
        		System.err.println("Success DELTE: failed!");

        	if(tester.failDelete())
            	System.out.println("Fail DELETE: passed!");
        	else
        		System.err.println("Fail DELETE: failed!");

        	if(tester.successLongPostGet())
            	System.out.println("Success long POST&GET: passed!");
        	else
        		System.err.println("Success long POST&GET: failed!");
        	
        } finally {
            tester.close();
        }
        
    }

	private void close() throws IOException {
		httpclient.close();
	}

	private boolean successPost() throws Exception {
		boolean res = false;
    	//Success POST
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1L))
		        .build();
        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(new StringEntity(Long.toHexString(1L)));
        CloseableHttpResponse postResponse = httpclient.execute(postMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 201 || 
        		postResponse.getStatusLine().getStatusCode() == 202)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }

            HttpEntity postEntity = postResponse.getEntity();
            EntityUtils.consume(postEntity);
        } finally {
            postResponse.close();
        }
        return res;
	}

	private boolean failPost() throws Exception
	{
		boolean res = false;
    	//Fail POST
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1L))
		        .build();
        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(new StringEntity(Long.toHexString(1L)));
        CloseableHttpResponse postResponse = httpclient.execute(postMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 404)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }

            HttpEntity postEntity = postResponse.getEntity();
            EntityUtils.consume(postEntity);
        } finally {
            postResponse.close();
        }
        return res;
	}
	
	private boolean successGet() throws Exception
	{
		boolean res = false;
		
		//Success GET
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1L))
		        .build();
        HttpGet getMethod = new HttpGet(uri);
        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 200 && 
            		getEntity.equals(Long.toString(1L)))
            {
            	res = true;
            }
            else
            {
            	res = false;
            }
            EntityUtils.consume(getEntity);
        } finally {
            getResponse.close();
        }

		return res;
	}
	
	private boolean failGet() throws Exception
	{
		boolean res = false;
		//Fail GET
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(2L))
		        .build();

        HttpGet getMethod = new HttpGet(uri);
        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 404 ||
        		getResponse.getStatusLine().getStatusCode() == 400)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }
            EntityUtils.consume(getEntity);
        } finally {
            getResponse.close();
        }

		return res;
	}
	
	private boolean successPut() throws Exception
	{
		boolean res = false;
    	//Success PUT
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1L))
		        .build();

        HttpPut putMethod = new HttpPut(uri);
        putMethod.setEntity(new StringEntity(Long.toHexString(1001L)));
        CloseableHttpResponse postResponse = httpclient.execute(putMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 200 ||
               postResponse.getStatusLine().getStatusCode() == 204)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }

            HttpEntity postEntity = postResponse.getEntity();
            EntityUtils.consume(postEntity);
        } finally {
            postResponse.close();
        }

		return res;
	}
	
	private boolean failPut() throws Exception
	{
		boolean res = false;
    	//Fail PUT
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(2L))
		        .build();

        HttpPut putMethod = new HttpPut(uri);
        putMethod.setEntity(new StringEntity(Long.toHexString(2L)));
        CloseableHttpResponse postResponse = httpclient.execute(putMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 404)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }

            HttpEntity postEntity = postResponse.getEntity();
            EntityUtils.consume(postEntity);
        } finally {
            postResponse.close();
        }

		return res;
	}
	
	private boolean successDelete() throws Exception
	{
		boolean res = false;
		//Success DELETE
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1L))
		        .build();

        HttpDelete delMethod = new HttpDelete(uri);
        CloseableHttpResponse getResponse = httpclient.execute(delMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 200 ||
        		getResponse.getStatusLine().getStatusCode() == 202 ||
        		getResponse.getStatusLine().getStatusCode() == 204)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }
            EntityUtils.consume(getEntity);
        } finally {
            getResponse.close();
        }

		return res;
	}
	
	private boolean failDelete() throws Exception
	{
		boolean res = false;
		//Fail DELETE
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(2L))
		        .build();

        HttpDelete delMethod = new HttpDelete(uri);
        CloseableHttpResponse getResponse = httpclient.execute(delMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 404)
            {
            	res = true;
            }
            else
            {
            	res = false;
            }
            EntityUtils.consume(getEntity);
        } finally {
            getResponse.close();
        }

		return res;
	}
	
	private boolean successLongPostGet() throws Exception {
		boolean res = false;
		String longString = "0\n23456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0\r23456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0\r\n3456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
    	//Success POST
		URI uri = new URIBuilder()
		        .setScheme("http")
		        .setHost(baseUrl)
		        .setPath(Long.toString(1002L))
		        .build();

        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(new StringEntity(longString));
        CloseableHttpResponse postResponse = httpclient.execute(postMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() != 200)
            {
            	res = false;
            }
            else
            {
            	res = true;
            }

            HttpEntity postEntity = postResponse.getEntity();
            EntityUtils.consume(postEntity);
        } finally {
            postResponse.close();
        }

        if(res)
        {
			//Success GET
	        HttpGet getMethod = new HttpGet(uri);
	        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
	        try {
	            System.out.println(getResponse.getStatusLine());
	            HttpEntity getEntity = getResponse.getEntity();
	            if(getResponse.getStatusLine().getStatusCode() == 200 && getEntity.equals(longString))
	            {
	            	res = true;
	            }
	            else
	            {
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
