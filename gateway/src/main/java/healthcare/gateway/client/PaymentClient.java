package healthcare.gateway.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.AppoinmentDTO;
import dto.PaymentDTO;
import utility.IpMapperDTO;
import utility.IpMapperModel;
import utility.Rcode;

public class PaymentClient {

	
	String API;
	Client client = ClientBuilder.newClient();
	
	
	public PaymentClient() {
		super();
		IpMapperModel iModel = new IpMapperModel();
		IpMapperDTO iMapperDTO = iModel.getIpMapperDTO();
		API = iMapperDTO.getPaymentIP().trim();
	}

	
	
	
	public Response getPaymentData() {
		
		WebTarget service = client.target(API);
		try {
			Response response = service.request(MediaType.APPLICATION_JSON).get();
			return response;
		} catch (ProcessingException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response InsertIntoPayments(PaymentDTO paymentDTO) {
		AppointmentClient cAppointmentClient = new AppointmentClient();
		System.out.println(API);
		WebTarget service = client.target(API).path("add");
		try {
			AppoinmentDTO dt=new AppoinmentDTO();
			dt.setApp_id(paymentDTO.getApp_id());
			//cAppointmentClient.UpdateAppoinment(dt);
			try {
				System.out.println("ID price:-"+paymentDTO.getApp_id());
				AppoinmentDTO dto = cAppointmentClient.getPaymentPendingList(paymentDTO.getApp_id()).readEntity(AppoinmentDTO.class);
				//cAppointmentClient.UpdateAppoinment(dt);
				System.out.println(dto.getApp_id());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dateobj = new Date();

				String currentData = df.format(dateobj);
				paymentDTO.setDate_time(currentData);
				
				paymentDTO.setDoc_id(dto.getApp_doc_id());
				paymentDTO.setApp_id(dto.getApp_id());
				paymentDTO.setAmount(dto.getApp_price());
				paymentDTO.setDoc_fee((dto.getApp_price() / 100)*20);
				paymentDTO.setHos_fee((dto.getApp_price() / 100)*80);
				paymentDTO.setHos_id(dto.getApp_session_id());
				paymentDTO.setReference_No(ThreadLocalRandom.current().nextInt());
				
			} catch (Exception e) {
				return Rcode.No_content("Invalid Appoinment ID");
			}
			
			Response response = service.request(MediaType.APPLICATION_JSON).post(Entity.json(paymentDTO));
			
			return response;
		} catch (ProcessingException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response UpdatePayment(PaymentDTO paymentDTO) {
		System.out.println(API);
		WebTarget service = client.target(API).path("update").path(String.valueOf(paymentDTO.getPayment_id()));
		try {
			Response response = service.request(MediaType.APPLICATION_JSON).put(Entity.json(paymentDTO));
			return response;
		} catch (ProcessingException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}


	public Response DeletePayment(int id) {
		System.out.println(API);
		WebTarget service = client.target(API).path("delete").path(String.valueOf(id));
		try {
			Response response = service.request(MediaType.APPLICATION_JSON).delete();
			return response;
		} catch (ProcessingException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
