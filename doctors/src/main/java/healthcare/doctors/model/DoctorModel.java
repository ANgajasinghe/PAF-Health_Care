package healthcare.doctors.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.DoctorDTO;
import healthcare.doctors.DTO.SpecificationDTO;
import utility.ConnectionBuilder;
import utility.Messages;

public class DoctorModel implements IDataModel {

	private final ConnectionBuilder cBuilder = new ConnectionBuilder();
	// private Connection MYSQLcon = cBuilder.MYSQLConnection();

	@Override
	public boolean connectionChecker(Connection MYSQLcon) {

		if (MYSQLcon == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<DoctorDTO> getSepecificationAllData() {

		List<DoctorDTO> specificationDTOliList = new ArrayList<DoctorDTO>();
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		if (this.connectionChecker(MYSQLcon)) {
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append(SpecificationDTO.specification_id + ",");
			sBuilder.append(SpecificationDTO.specification_name + ",");
			sBuilder.append(SpecificationDTO.specification_dis + "\n");
			sBuilder.append("FROM\n");
			sBuilder.append(SpecificationDTO.TABE_NAME);

			String qurtString = sBuilder.toString();

			try {
				Statement stmt = MYSQLcon.createStatement();
				ResultSet rs = stmt.executeQuery(qurtString);

				while (rs.next()) {
					DoctorDTO dto = new DoctorDTO();
					dto.setSpecification_id(rs.getInt(SpecificationDTO.specification_id));
					dto.setSpecification_name(rs.getString(SpecificationDTO.specification_name));
					dto.setSpecification_dis((rs.getString(SpecificationDTO.specification_dis) != null)
							? rs.getString(SpecificationDTO.specification_dis)
							: Messages.NODATA);
					specificationDTOliList.add(dto);
				}

				return specificationDTOliList;

			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
				return specificationDTOliList;
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return specificationDTOliList;

	}

	@Override
	public List<DoctorDTO> getAllDoctors() {
		List<DoctorDTO> allDocList = new ArrayList<DoctorDTO>();
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		if (this.connectionChecker(MYSQLcon)) {

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append("*\t");
			sBuilder.append("FROM\n");
			sBuilder.append("doctors");

			String qurtString = sBuilder.toString();

			try {
				Statement stmt = MYSQLcon.createStatement();
				ResultSet rs = stmt.executeQuery(qurtString);

				while (rs.next()) {
					DoctorDTO dto = new DoctorDTO();
					dto.setDoc_id(rs.getInt("doc_id"));
					dto.setDoc_reg_no(rs.getString("doc_reg_no"));
					dto.setDoc_first_name(rs.getString("doc_first_name"));
					dto.setDoc_last_name(rs.getString("doc_last_name"));
					dto.setDoc_address_no(rs.getString("doc_address_no"));
					dto.setDoc_address_lane1(rs.getString("doc_address_lane1"));
					dto.setDoc_address_lane2(rs.getString("doc_address_lane2"));
					dto.setDoc_address_lane3(rs.getString("doc_address_lane3"));
					dto.setDoc_city(rs.getString("doc_city"));
					dto.setDoc_tp1(rs.getString("doc_tp1"));
					dto.setDoc_tp2(rs.getString("doc_tp2"));
					dto.setDoc_tp3(rs.getString("doc_tp3"));
					dto.setDoc_email(rs.getString("doc_email"));
					dto.setDoc_status_id(rs.getInt("doc_status_id"));
					dto.setSpecification_id(rs.getInt("doc_specification_id"));
					allDocList.add(dto);
				}

				return allDocList;

			} catch (SQLException e) {
				DoctorDTO dto = new DoctorDTO();
				dto.setSpecification_dis(e.toString());
				allDocList.add(dto);
				return allDocList;
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return null;
	}

	@Override
	public String insertIntoDoctors(DoctorDTO doctorDTOs) {
		Connection MYSQLcon = cBuilder.MYSQLConnection();
		if (this.connectionChecker(MYSQLcon)) {

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("INSERT INTO doctors ( \n");
			sBuilder.append("doc_reg_no,");
			sBuilder.append("doc_first_name,");
			sBuilder.append("doc_last_name,");
			sBuilder.append("doc_address_no,");
			sBuilder.append("doc_address_lane1,");
			sBuilder.append("doc_address_lane2,");
			sBuilder.append("doc_address_lane3,");
			sBuilder.append("doc_city,");
			sBuilder.append("doc_tp1,");
			sBuilder.append("doc_tp2,");
			sBuilder.append("doc_tp3,");
			sBuilder.append("doc_email,");
			sBuilder.append("doc_status_id,");
			sBuilder.append("doc_specification_id)\n");
			sBuilder.append("VALUES (\n");
			sBuilder.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?\n");
			sBuilder.append(")");

			String queryString = sBuilder.toString();
			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(queryString);
				pStatement.setString(1, doctorDTOs.getDoc_reg_no() != null ? doctorDTOs.getDoc_reg_no() : null);
				pStatement.setString(2, doctorDTOs.getDoc_first_name() != null ? doctorDTOs.getDoc_first_name() : null);
				pStatement.setString(3, doctorDTOs.getDoc_last_name() != null ? doctorDTOs.getDoc_last_name() : null);

				pStatement.setString(4, doctorDTOs.getDoc_address_no() != null ? doctorDTOs.getDoc_address_no() : null);
				pStatement.setString(5,
						doctorDTOs.getDoc_address_lane1() != null ? doctorDTOs.getDoc_address_lane1() : null);
				pStatement.setString(6,
						doctorDTOs.getDoc_address_lane2() != null ? doctorDTOs.getDoc_address_lane2() : null);
				pStatement.setString(7,
						doctorDTOs.getDoc_address_lane3() != null ? doctorDTOs.getDoc_address_lane3() : null);
				pStatement.setString(8, doctorDTOs.getDoc_city() != null ? doctorDTOs.getDoc_city() : null);

				pStatement.setString(9, doctorDTOs.getDoc_tp1() != null ? doctorDTOs.getDoc_tp1() : null);
				pStatement.setString(10, doctorDTOs.getDoc_tp2() != null ? doctorDTOs.getDoc_tp2() : null);
				pStatement.setString(11, doctorDTOs.getDoc_tp3() != null ? doctorDTOs.getDoc_tp3() : null);
				pStatement.setString(12, doctorDTOs.getDoc_email() != null ? doctorDTOs.getDoc_email() : null);

				pStatement.setInt(13, doctorDTOs.getDoc_status_id() != null ? doctorDTOs.getDoc_status_id() : 0);
				pStatement.setInt(14, doctorDTOs.getSpecification_id() != null ? doctorDTOs.getSpecification_id() : 0);

				boolean result = pStatement.execute();

				if (!result) {
					boolean val = this.insertIntoDocHospital(doctorDTOs.getHospital_list(), doctorDTOs.getDoc_reg_no());
					
					if (val) {
						return Messages.docAndHostSuccess;
					}
					return Messages.doctorSuccess;
				}
			} catch (SQLException e) {
				return e.toString();
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return Messages.connectionER;

	}

	@Override
	public boolean insertIntoDocHospital(String Hospitals,String RegNo) {
		
		boolean result = false;
		if (Hospitals == null || RegNo == null) {
			return false;
		}else {
			String docId = this.SelectDocId(RegNo);
			if (docId != null) {
				Connection MYSQLcon  = cBuilder.MYSQLConnection();
				String[] hostArr = Hospitals.split(",");
				
				StringBuilder sBuilder = new StringBuilder();
				sBuilder.append("INSERT INTO doc_hospital ( \n");
				sBuilder.append("doc_id,\n");
				sBuilder.append("hostpital_id )\n");
				sBuilder.append("VALUES(?,?)");
				
				try {
					
					for (int i = 0; i < hostArr.length; i++) {
						String qurtString = sBuilder.toString();
						PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
						pStatement.setInt(1,Integer.parseInt(hostArr[i]));
						pStatement.setInt(2,Integer.parseInt(docId));
						result = pStatement.execute();
						if (result) {
							return false;
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}finally {
					try {
						MYSQLcon.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (!result) {
				return true;
			}
		}
		return result;

	}

	@Override
	public String SelectDocId(String regNO) {
		if (regNO != null) {
			Connection MYSQLcon = cBuilder.MYSQLConnection();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("SELECT\n");
			sBuilder.append("doc_id\n");
			sBuilder.append("FROM\n");
			sBuilder.append("doctors\n");
			sBuilder.append("WHERE doc_reg_no = ?");
			String qurtString = sBuilder.toString();

			try {
				PreparedStatement pStatement = MYSQLcon.prepareStatement(qurtString);
				pStatement.setString(1, regNO.trim());
				ResultSet rs = pStatement.executeQuery();
				while (rs.next()) {
					return rs.getString("doc_id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					MYSQLcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
