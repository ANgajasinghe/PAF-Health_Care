package healthcare.doctors.model;

import java.util.List;


import healthcare.doctors.DTO.DoctorDTO;


public interface IDataModel {
	
	public boolean connectionChecker();
	public List<DoctorDTO> getSepecificationAllData();
	public List<DoctorDTO> getAllDoctors();
	public String insertIntoDoctors(DoctorDTO doctorDTOs);
	
}
