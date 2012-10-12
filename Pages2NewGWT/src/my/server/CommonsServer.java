package my.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

public class CommonsServer {
	public static final Logger LOG=Logger.getLogger(CommonsServer.class);
	public static String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	public static ObjectId normalizeID (String id) {
		if (id==null||id.equals("")) {
			//id = null;
			return null;
		}
		else {
			return new ObjectId(id.trim());
		}
	
	}
	
	
	public static String fromIDtoString (ObjectId objId) {
		if (objId==null) {
			//id = null;
			return "";
		}
		else {
			return objId.toString();
		}
	
	}
	
	
	
	
	public static String getParams(String paramsLine, String vartoget) {
		String value = null;
		String delimiter = "&";
		/* given string will be split by the argument delimiter provided. */
		String[] paramsArray;
		
		paramsArray = paramsLine.split(delimiter);
		for (int i=0;i<paramsArray.length; i++) {
			
			
			String delimiter2 = "=";
			String[] paramsArray2;
			paramsArray2 = paramsArray[i].split(delimiter2);
			if (paramsArray2[0].trim().equals(vartoget)) {
				
				 value = paramsArray2[1];
				 return value;
			}
		}
		return null;
	}
	
	
	public static String urlReader(String urlString) throws IOException {
		    URL oracle = new URL(urlString);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            //System.out.println(inputLine);
	        in.close();
	        
	        return inputLine;
	}

	
	public static String readPage(URL url) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        LOG.info("url.toURI()= " + url.toURI());
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);

        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuffer sb = new StringBuffer();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }

            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	
	
	public static String postRequest(String targetURL, String postParams) {
	    URL url;
	    HttpURLConnection connection = null;
	    try {
	        // Create connection
	        url = new URL(targetURL);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");

	        connection.setRequestProperty("Content-Length", ""
	                + Integer.toString(postParams.getBytes().length));
	        connection.setRequestProperty("Content-Language", "en-US");

	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);

	        // Send request
	        DataOutputStream wr = new DataOutputStream(connection
	                .getOutputStream());
	        wr.writeBytes(postParams);
	        wr.flush();
	        wr.close();

	        // Get Response
	        InputStream is = connection.getInputStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer();
	        while ((line = rd.readLine()) != null) {
	            response.append(line);
	            response.append('\r');
	        }
	        rd.close();
	        return response.toString();

	    } catch (Exception e) {

	        e.printStackTrace();
	        return null;

	    } finally {

	        if (connection != null) {
	            connection.disconnect();
	        }
	    }
	}

	
	
	public static String postHTTPJakarta (String targetURL, List <NameValuePair> nvps) {
		String response = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(targetURL);
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		HttpResponse response2 = null; 
		try {
			response2 = httpclient.execute(httpPost);
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity2);
		} catch (IOException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    //httpPost.releaseConnection();
		}
		
		    return response;
	}
	
	
	public static void postMultiPartHTTPJakarta (String targetURL, String fileUrl, HashMap<String,String> params, String fileName, String fileMimeType)  {
		//String response = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(targetURL);
		//File file = new File("misc/plant4.jpg");
		
		
		
		URL imgUrl = null;
		try {
			//imgUrl = new URL("http://www.pinbelle.com/extranewgwt/getphoto?photoid=5075ddade4b02821650bf8be");
			imgUrl = new URL(fileUrl);
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace(); 
		}
		try {
			imgUrl.openConnection();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace(); 
		}
		InputStream reader = null; 
		try {
			reader = imgUrl.openStream();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace(); 
		}

	

		byte[] buffer = new byte[153600];
		int totalBytesRead = 0;
		int bytesRead = 0; 


		////System.out.println("Reading ZIP file 150KB blocks at a time.\n");

		ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();

		try {
			while ((bytesRead = reader.read(buffer)) > 0)
			{ 
				//writer.write(buffer, 0, bytesRead);
				buffer2.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
				//bufferAll

				totalBytesRead += bytesRead; 
			}
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		//buffer2.toByteArray()
		
		
		
		MultipartEntity mpEntity = new MultipartEntity();
		//ContentBody cbFile = new ByteArrayBody(buffer2.toByteArray(), "image/jpeg", "file");
		ContentBody cbFile = new ByteArrayBody(buffer2.toByteArray(), fileMimeType, fileName);
	    //ContentBody cbFile = new FileBody(file, "image/jpeg");
	    
		/*
		ContentBody cbText = null; 
		try {
			cbText = new StringBody("ololo");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace(); 
		}*/
		
		
		mpEntity.addPart("source", cbFile);
		
		//params
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry <String, String> pairs = (Entry<String, String>)it.next();
	        ContentBody cbParam = null; 
			try {
				cbParam = new StringBody(pairs.getValue());
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace(); 
			}
	        mpEntity.addPart(pairs.getKey(), cbParam);
	        //System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }

	    //mpEntity.addPart("source", cbFile);
	    //mpEntity.addPart("message", cbText);
	    


	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = null; 
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); 
		}
	    HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      try {
			System.out.println(EntityUtils.toString(resEntity));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    if (resEntity != null) {
	      //resEntity.consumeContent();
	    }

	    httpclient.getConnectionManager().shutdown();

		
		    //return response;
	}
	
	

}
