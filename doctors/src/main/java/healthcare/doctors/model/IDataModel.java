package healthcare.doctors.model;

import java.sql.Connection;
import java.util.List;

import dto.DoctorDTO;


public interface IDataModel {
	
	public boolean connectionChecker(Connection MYSQLcon);
	public List<DoctorDTO> getSepecificationAllData();
	public List<DoctorDTO> getAllDoctors();
	public String insertIntoDoctors(DoctorDTO doctorDTOs);
	public boolean insertIntoDocHospital(String Hospitals,String RegNo);
	public String SelectDocId(String regNO);
	public DoctorDTO SelectDocById(String id);
	public String DocWorkAssign(DoctorDTO doctorDTO);
	
	public String DeleteDocAll(int docID);
	public String UpdateDoc(String docID , DoctorDTO dto);
	
	
}
