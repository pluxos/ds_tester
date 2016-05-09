package thrift;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import lamedb.KeyValue;
import lamedb.LameDB;
import lamedb.lamedbConstants;

public class ThriftTester {

	static int testLen = 20;
	private static LameDB.Client client;

	public final static void main(String[] args) throws Exception {
		try {
			TTransport transport = new TSocket(args[0], Integer.parseInt(args[1]));
			transport.open();

			TProtocol protocol = new  TBinaryProtocol(transport);
			client = new LameDB.Client(protocol);

			ExecutorService executor = Executors.newFixedThreadPool(100);
			Set<Callable<Boolean>> callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 0; id < 1*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successPost(myId);
					}
				});
			}

			List<Future<Boolean>> futures = executor.invokeAll(callables);
			boolean result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success PUT: passed!");
			else
				System.err.println("Success PUT: failed!");        		






			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 0; id < 1*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.failPost(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Fail PUT: passed!");
			else
				System.err.println("Fail PUT: failed!");        		






			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 0; id < 1*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successGet(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success GET: passed!");
			else
				System.err.println("Success GET: failed!");


			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 1*testLen; id < 2*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.failGet(myId);
					}
				});        		
			}




			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Fail GET: passed!");
			else
				System.err.println("Fail GET: failed!");


			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 0; id < 1*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successUpdate(myId);
					}
				});        		
			}




			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success UPDATE: passed!");
			else
				System.err.println("Success UPDATE: failed!");

			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 1*testLen; id < 2*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.failUpdate(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Fail UPDATE: passed!");
			else
				System.err.println("Fail UPDATE: failed!");






			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 0; id < 1*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successRemove(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success REMOVE: passed!");
			else
				System.err.println("Success REMOVE: failed!");






			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 1*testLen; id < 2*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.failRemove(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Fail REMOVE: passed!");
			else
				System.err.println("Fail REMOVE: failed!");

			
			

			
			
			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 5*testLen; id < 6*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successLongPostGet(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success long PUT&GET: passed!");
			else
				System.err.println("Success long PUT&GET: failed!");

			
			
			
			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 6*testLen; id < 7*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successUpdateWithVersion(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success long UPDATE WITH VERSION: passed!");
			else
				System.err.println("Success long UPDATE WITH VERSION: failed!");


			
			
			
			executor = Executors.newFixedThreadPool(100);
			callables = new HashSet<Callable<Boolean>>(); 
			for(long id = 7*testLen; id < 8*testLen; id++)
			{
				final ThriftTester tester = new ThriftTester();
				final long myId = id;
				callables.add(new Callable<Boolean>(){
					public Boolean call() throws Exception {
						return tester.successRemoveWithVersion(myId);
					}
				});        		
			}

			futures = executor.invokeAll(callables);
			result = true;
			for(Future<Boolean> f : futures) {
				result = result && f.get();

				if(!result)
					break;
			}

			if(result)
				System.out.println("Success long PUT&GET: passed!");
			else
				System.err.println("Success long PUT&GET: failed!");

			
			transport.close();
		} catch (TException x) {
			x.printStackTrace();	
		} finally {

		}
	}

	private boolean successPost(long id) throws Exception {
		int resp = client.put(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp == 0)
			return true;
		else
			return false;
	}

	/*
	 * A especificacao atual nao permite diferenciar sucesso de insercao
	 * com falha em que o dado estava na versao 0.
	 */
	private boolean failPost(long id) throws Exception 
	{
		int resp = client.put(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp == 0)
			return true;
		else
			return false;
	}


	private boolean successGet(long id) throws Exception 
	{
		KeyValue resp = client.get(id);
		if(resp.key == 0) //&& resp.value == id && resp.version == 0
			return true;
		else
			return false;
	}

	private boolean failGet(long id) throws Exception
	{
		KeyValue resp = client.get(id);
		if(resp.version == -1)
			return true;
		else
			return false;
	}

	private boolean successUpdate(long id) throws Exception {
		int resp = client.update(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp == 1)
			return true;
		else
			return false;
	}

	private boolean successUpdateWithVersion(long id) throws Exception {
		int resp = client.put(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp != 0)
		{	
			return false;
		}
		else
		{		
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),0);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),1);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),2);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),3);
			if(resp == 4)
				return true;
			else
				return false;
		}
	}

	private boolean successRemoveWithVersion(long id) throws Exception {
		int resp = client.put(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp == 0)
		{	
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),0);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),1);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),2);
			resp = client.updateWithVersion(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()),3);
			if(resp == 4)
			{
				KeyValue resp1 = client.removeWithVersion(id,0);
				if(resp1.version == 4 )//&& resp.value == id && resp.version == 0
				{
					return true;
				}
			}
		}
		return false;
	}

	private boolean failUpdate(long id) throws Exception 
	{
		int resp = client.update(id, ByteBuffer.wrap(Long.toHexString(id).getBytes()));
		if(resp == -1)
			return true;
		else
			return false;
	}


	private boolean successRemove(long id) throws Exception
	{
		KeyValue resp = client.remove(id);
		if(resp.version == 1) // && data
			return true;
		else
			return false;
	}

	private boolean failRemove(long id) throws Exception 
	{
		KeyValue resp = client.remove(id);
		if(resp.version == -1)
			return true;
		else
			return false;
	}




	private boolean successLongPostGet(long id) throws Exception 
	{
		byte [] data = new byte[lamedbConstants.MAX_VALUE_LEN];
		int resp = client.put(id, ByteBuffer.wrap(data));
		if(resp == 0)
		{
			KeyValue data2 = client.get(id);
			if(data2.version == 0)
				if(data2.value.equals(data))
					return true;
		}
		return false;
	}
}
