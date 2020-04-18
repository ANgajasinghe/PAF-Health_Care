package healthcare.gateway.client;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.AppoinmentDTO;
import dto.DoctorDTO;
import healthcare.gateway.auth.AuthFilter;
import utility.IpMapperDTO;
import utility.IpMapperModel;
import utility.Rcode;

public class AppointmentClient{
		
	String API;
	Client client = ClientBuilder.newClient();
	
	

	public AppointmentClient() {
		super();
		IpMapperModel iModel = new IpMapperModel();
		IpMapperDTO iMapperDTO = iModel.getIpMapperDTO();
		API = iMapperDTO.getAppoimentIP();
	}
	
	public final Response insertIntoAppoiment(AppoinmentDTO dto) {
		
		DoctorClient dClient =new DoctorClient();
		HospitalClient hsClient = new HospitalClient();
		DoctorDTO doctorDTO = null;
		try {
			doctorDTO = dClient.GET_SESSION_DATA_FOR_APPOINMENT_SERVICE(dto.getApp_session_id())
						.readEntity(DoctorDTO.class);
			doctorDTO.setHospital_name(hsClient.getHospitalNameByID(String.valueOf(doctorDTO.getHospital_id()))
					.readEntity(String.class));
		} catch (Exception e) {
			return Rcode.No_content("Invalide Session Id");
		}
		
		try {
			dto.setApp_patient_id(Integer.valueOf(AuthFilter.CurrentAuthUserId));
		} catch (Exception e) {
			return Rcode.UNAUTHORIZED("Only Patients can use this service");
		}
		
		System.out.println(doctorDTO.getDoc_id());
		
		WebTarget service = client.target(API).path("add");
		System.out.println(API);
		try {
			dto.setApp_doc_id(doctorDTO.getDoc_id());
			dto.setApp_hospital_name(doctorDTO.getHospital_name());
			dto.setApp_price(doctorDTO.getPrice());
			dto.setApp_payment_status("pending");
			Response response = service.request(MediaType.APPLICATION_JSON).post(Entity.json(dto));
	
			return response;
		}catch (ProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}


}