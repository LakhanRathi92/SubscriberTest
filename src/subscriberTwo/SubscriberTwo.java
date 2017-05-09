package subscriberTwo;
import java.io.IOException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class SubscriberTwo {

	public static void main(String[] args){
		
		String Query2 = "PREFIX ssn: <http://www.w3.org/2005/Incubator/ssn/ssnx/ssn/> "+
				"PREFIX DUL: <http://www.ontologydesignpatterns.org/ont/dul/DUL.owl/> " +
				"PREFIX ex: <http://www.sensordescription.de/inrdf/> " +
				"PREFIX foo: <http://www.example.com/>" 
		+ "CONSTRUCT { "
		+ " ?d foo:reading ?v }"
		+ " WHERE "
		+ "{"
		+ " ?d a ssn:Sensor ."
//		+ "	?d ex:type ex:TemperatureSensor ."
		+ "?d ex:type ex:ProximitySensor ."
		+ " ?d DUL:hasDataValue ?v" 
		+ " }" ;
	
		int qos             = 0;
        String broker       = "ws://localhost:8080";
        String clientId2     = "JavaSampleSubscriber2";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
	    	MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId2, persistence);
	        MqttConnectOptions connOpts = new MqttConnectOptions();        
	        connOpts.setCleanSession(true);
	        System.out.println("Connecting to broker: "+broker);
	        sampleClient.connect(connOpts);
	        System.out.println("Connected");
	        
	        //put a better solution
	        do{
	        	
	        }while(!sampleClient.isConnected());
	        
	      
	        sampleClient.subscribe(Query2, qos);
	        sampleClient.setCallback(new MqttCallback() {
		        @Override
		    	public void connectionLost(Throwable cause) {
		    		// TODO Auto-generated method stub
		    		
		    	}		
		    	@Override
		    	public void messageArrived(String topic, MqttMessage message) throws Exception {
		    		// TODO Auto-generated method stub
		    		byte[] msg = message.getPayload();
		    		System.out.print("Message : " + new String(msg, "UTF-8"));
		    	}
		    	@Override
		    	public void deliveryComplete(IMqttDeliveryToken token) {
		    		// TODO Auto-generated method stub
		    		
	    		}
	        });
            } catch(MqttException me) {
	            System.out.println("reason "+me.getReasonCode());
	            System.out.println("msg "+me.getMessage());
	            System.out.println("loc "+me.getLocalizedMessage());
	            System.out.println("cause "+me.getCause());
	            System.out.println("excep "+me);
	            me.printStackTrace();
	        }   		
	}
}