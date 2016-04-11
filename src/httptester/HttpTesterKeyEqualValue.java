package httptester;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class HttpTesterKeyEqualValue {
    private CloseableHttpClient httpclient;
	private String baseUrl;

    public HttpTesterKeyEqualValue(String url) {
        httpclient = HttpClients.createDefault();
        baseUrl = url;
	}

	public final static void main(String[] args) throws Exception {
    	HttpTesterKeyEqualValue tester = new HttpTesterKeyEqualValue("http://"+ args[0] + ":" + args[1] + "/");
    	
        try 
        {
        	if(tester.successPost())
            	System.err.println("Success POST: passed!");
        	else
        		System.out.println("Success POST: failed!");

        	if(tester.failPost())
            	System.err.println("Fail POST: passed!");
        	else
        		System.out.println("Fail POST: failed!");

        	
        	if(tester.successGet())
            	System.err.println("Success GET: passed!");
        	else
        		System.out.println("Success GET: failed!");

        	if(tester.failGet())
            	System.err.println("Fail GET: passed!");
        	else
        		System.out.println("Fail GET: failed!");
        	
        	if(tester.successPut())
            	System.err.println("Success PUT: passed!");
        	else
        		System.out.println("Success PUT: failed!");

        	if(tester.failPut())
            	System.err.println("Fail PUT: passed!");
        	else
        		System.out.println("Fail PUT: failed!");
        	
        	if(tester.successDelete())
            	System.err.println("Success DELETE: passed!");
        	else
        		System.out.println("Success DELTE: failed!");

        	if(tester.failDelete())
            	System.err.println("Fail DELETE: passed!");
        	else
        		System.out.println("Fail DELETE: failed!");

        	if(tester.successLongPostGet())
            	System.err.println("Success long POST&GET: passed!");
        	else
        		System.out.println("Success long POST&GET: failed!");
        	
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
        HttpPost postMethod = new HttpPost(baseUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(Long.toString(1L), Long.toHexString(1L)));
        postMethod.setEntity(new UrlEncodedFormEntity(nvps));
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
        return res;
	}

	private boolean failPost() throws Exception
	{
		boolean res = false;
    	//Fail POST
        HttpPost postMethod = new HttpPost(baseUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(Long.toString(1L), Long.toHexString(1L)));
        postMethod.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse postResponse = httpclient.execute(postMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 200)
            {
            	res= false;
            }
            else
            {
            	res =true;
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
        HttpGet getMethod = new HttpGet(baseUrl+Long.toString(1L));
        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 200 && 
            		EntityUtils.toString(getEntity).equals(Long.toString(1L)))
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
        HttpGet getMethod = new HttpGet(baseUrl+Long.toString(2L));
        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() != 200)
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
        HttpPut putMethod = new HttpPut(baseUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(Long.toString(1L), Long.toHexString(1001L)));
        putMethod.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse postResponse = httpclient.execute(putMethod);

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

		return res;
	}
	
	private boolean failPut() throws Exception
	{
		boolean res = false;
    	//Fail PUT
        HttpPut putMethod = new HttpPut(baseUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(Long.toString(2L), Long.toHexString(2L)));
        putMethod.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse postResponse = httpclient.execute(putMethod);

        try {
            System.out.println(postResponse.getStatusLine());
            if(postResponse.getStatusLine().getStatusCode() == 200)
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

		return res;
	}
	
	private boolean successDelete() throws Exception
	{
		boolean res = false;
		//Success DELETE
        HttpDelete delMethod = new HttpDelete(baseUrl+Long.toString(1L));
        CloseableHttpResponse getResponse = httpclient.execute(delMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() == 200)
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
        HttpDelete delMethod = new HttpDelete(baseUrl+Long.toString(2L));
        CloseableHttpResponse getResponse = httpclient.execute(delMethod);
        try {
            System.out.println(getResponse.getStatusLine());
            HttpEntity getEntity = getResponse.getEntity();
            if(getResponse.getStatusLine().getStatusCode() != 200)
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
		String longString = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"+
							"0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
    	//Success POST
        HttpPost postMethod = new HttpPost(baseUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(Long.toString(1002L), longString));
        postMethod.setEntity(new UrlEncodedFormEntity(nvps));
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
	        HttpGet getMethod = new HttpGet(baseUrl+Long.toString(1002L));
	        CloseableHttpResponse getResponse = httpclient.execute(getMethod);
	        try {
	            System.out.println(getResponse.getStatusLine());
	            HttpEntity getEntity = getResponse.getEntity();
	            if(getResponse.getStatusLine().getStatusCode() == 200 && 
	            		EntityUtils.toString(getEntity).equals(longString))
	            {
	            	res = true;
	            }
	            else
	            {
	            	System.out.println("status:"+getResponse.getStatusLine());
	            	System.out.println("entity:"+EntityUtils.toString(getEntity));
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
