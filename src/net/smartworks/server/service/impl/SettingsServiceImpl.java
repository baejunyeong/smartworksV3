package net.smartworks.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import net.smartworks.model.RecordList;
import net.smartworks.model.approval.Approval;
import net.smartworks.model.approval.ApprovalLine;
import net.smartworks.model.calendar.CompanyEvent;
import net.smartworks.model.calendar.WorkHour;
import net.smartworks.model.calendar.WorkHourPolicy;
import net.smartworks.model.community.Community;
import net.smartworks.model.community.User;
import net.smartworks.model.company.CompanyGeneral;
import net.smartworks.model.instance.info.InstanceInfoList;
import net.smartworks.model.instance.info.RequestParams;
import net.smartworks.model.service.ExternalForm;
import net.smartworks.model.service.Variable;
import net.smartworks.model.service.WSDLDetail;
import net.smartworks.model.service.WSDLOperation;
import net.smartworks.model.service.WSDLPort;
import net.smartworks.model.service.WebService;
import net.smartworks.server.engine.common.manager.IManager;
import net.smartworks.server.engine.common.model.Order;
import net.smartworks.server.engine.common.util.CommonUtil;
import net.smartworks.server.engine.config.manager.ISwcManager;
import net.smartworks.server.engine.config.model.SwcEventDay;
import net.smartworks.server.engine.config.model.SwcEventDayCond;
import net.smartworks.server.engine.config.model.SwcExternalForm;
import net.smartworks.server.engine.config.model.SwcExternalFormCond;
import net.smartworks.server.engine.config.model.SwcExternalFormParameter;
import net.smartworks.server.engine.config.model.SwcWebService;
import net.smartworks.server.engine.config.model.SwcWebServiceCond;
import net.smartworks.server.engine.config.model.SwcWebServiceParameter;
import net.smartworks.server.engine.config.model.SwcWorkHour;
import net.smartworks.server.engine.config.model.SwcWorkHourCond;
import net.smartworks.server.engine.factory.SwManagerFactory;
import net.smartworks.server.engine.organization.manager.ISwoManager;
import net.smartworks.server.engine.organization.model.SwoCompany;
import net.smartworks.server.engine.organization.model.SwoConfig;
import net.smartworks.server.engine.organization.model.SwoDepartment;
import net.smartworks.server.engine.organization.model.SwoUser;
import net.smartworks.server.engine.process.approval.manager.IAprManager;
import net.smartworks.server.engine.process.approval.model.AprApprovalDef;
import net.smartworks.server.engine.process.approval.model.AprApprovalDefCond;
import net.smartworks.server.engine.process.approval.model.AprApprovalLineDef;
import net.smartworks.server.engine.process.approval.model.AprApprovalLineDefCond;
import net.smartworks.server.service.ICommunityService;
import net.smartworks.server.service.ISettingsService;
import net.smartworks.server.service.util.ModelConverter;
import net.smartworks.util.LocalDate;
import net.smartworks.util.SmartUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements ISettingsService {

	private ISwoManager getSwoManager() {
		return SwManagerFactory.getInstance().getSwoManager();
	}
	private ISwcManager getSwcManager() {
		return SwManagerFactory.getInstance().getSwcManager();
	}
	private IAprManager getAprManager() {
		return SwManagerFactory.getInstance().getAprManager();
	}

	ICommunityService communityService;
	
	@Autowired
	public void setCommunityService(ICommunityService communityService) {
		this.communityService = communityService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.smartworks.service.impl.ISmartWorks#getMyDepartments(java.lang.String
	 * )
	 */
	@Override
	public CompanyGeneral getCompanyGeneral() throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();
			SwoCompany swoCompany = getSwoManager().getCompany(userId, companyId, IManager.LEVEL_ALL);
			SwoConfig swoConfig = getSwoManager().getConfig(userId, companyId, IManager.LEVEL_ALL);
			String id = swoCompany.getId();
			String name = swoCompany.getName();
			String logoName = getSwoManager().getLogo(userId, companyId);
			String sendMailHost = swoConfig.getSmtpAddress();
			String sendMailAccount = swoConfig.getUserId();
			String sendMailPassword = swoConfig.getPassword();
			boolean sendMailNotification = swoConfig.isActivity();
			CompanyGeneral companyGeneral = new CompanyGeneral();
			companyGeneral.setId(id);
			companyGeneral.setName(name);
			companyGeneral.setLogoName(logoName);
			companyGeneral.setSendMailHost(sendMailHost);
			companyGeneral.setSendMailAccount(sendMailAccount);
			companyGeneral.setSendMailPassword(sendMailPassword);
			companyGeneral.setSendMailNotification(sendMailNotification);
			companyGeneral.setTestAfterSaving(true);
			return companyGeneral;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public void setCompanyGeneral(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();
			String companyName = cUser.getCompany();

			Map<String, Object> frmCompanyGeneral = (Map<String, Object>)requestBody.get("frmCompanyGeneral");
	
			Set<String> keySet = frmCompanyGeneral.keySet();
			Iterator<String> itr = keySet.iterator();
			List<Map<String, String>> files = null;
			String txtMailHost = null;
			String txtMailAccount = null;
			String pasMailPassword = null;
			boolean isActivity = false;
			String imgCompanyLogo = null;
			String companyFileId = null;
			String companyFileName = null;

			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmCompanyGeneral.get(fieldId);
				if (fieldValue instanceof LinkedHashMap) {
					Map<String, Object> valueMap = (Map<String, Object>)fieldValue;
					if(fieldId.equals("imgCompanyLogo")) {
						files = (ArrayList<Map<String,String>>)valueMap.get("files");
					}
				} else if(fieldValue instanceof String) {					
					if(fieldId.equals("txtMailHost")) {
						txtMailHost = (String)frmCompanyGeneral.get("txtMailHost");
					} else if(fieldId.equals("txtMailAccount")) {
						txtMailAccount = (String)frmCompanyGeneral.get("txtMailAccount");
					} else if(fieldId.equals("pasMailPassword")) {
						pasMailPassword = (String)frmCompanyGeneral.get("pasMailPassword");
					} else if(fieldId.equals("chkMailNotification")) {
						isActivity = true;
					}
				}
			}

			if(!files.isEmpty()) {
				for(int i=0; i < files.subList(0, files.size()).size(); i++) {
					Map<String, String> fileMap = files.get(i);
					companyFileId = fileMap.get("fileId");
					companyFileName = fileMap.get("fileName");
					imgCompanyLogo = SwManagerFactory.getInstance().getDocManager().insertProfilesFile(companyFileId, companyFileName, companyId);
					getSwoManager().setLogo(userId, companyId, imgCompanyLogo);
				}
			}

			SwoConfig swoConfig = getSwoManager().getConfig(userId, companyId, IManager.LEVEL_ALL);

			swoConfig.setId(companyId);
			swoConfig.setName(companyName);
			swoConfig.setCompanyId(companyId);
			swoConfig.setSmtpAddress(txtMailHost);
			swoConfig.setUserId(txtMailAccount);
			swoConfig.setPassword(pasMailPassword);
			swoConfig.setActivity(isActivity);
			getSwoManager().setConfig(userId, swoConfig, IManager.LEVEL_ALL);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}

	int previousPageSize = 0;
	@Override
	public RecordList getWorkHourPolicyList(RequestParams params) throws Exception {

		try {
			RecordList recordList = new RecordList();
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			SwcWorkHourCond swcWorkHourCond = new SwcWorkHourCond();
			swcWorkHourCond.setCompanyId(companyId);

			long totalCount = getSwcManager().getWorkhourSize(userId, swcWorkHourCond);

			int pageSize = params.getPageSize();
			if(pageSize == 0) pageSize = 20;

			int currentPage = params.getCurrentPage();
			if(currentPage == 0) currentPage = 1;

			int totalPages = (int)totalCount % pageSize;

			if(totalPages == 0)
				totalPages = (int)totalCount / pageSize;
			else
				totalPages = (int)totalCount / pageSize + 1;

			int result = 0;

			if(params.getPagingAction() != 0) {
				if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXT10) {
					result = (((currentPage - 1) / 10) * 10) + 11;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXTEND) {
					result = totalPages;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREV10) {
					result = ((currentPage - 1) / 10) * 10;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREVEND) {
					result = 1;
				}
				currentPage = result;
			}

			if(previousPageSize != pageSize)
				currentPage = 1;

			previousPageSize = pageSize;

			if((long)((pageSize * (currentPage - 1)) + 1) > totalCount)
				currentPage = 1;

			if (currentPage > 0)
				swcWorkHourCond.setPageNo(currentPage-1);

			swcWorkHourCond.setPageSize(pageSize);

			swcWorkHourCond.setOrders(new Order[]{new Order("modificationDate", false)});
			SwcWorkHour[] swcWorkHours = getSwcManager().getWorkhours(userId, swcWorkHourCond, IManager.LEVEL_ALL); 
			List<WorkHourPolicy> workHourPolicyList = new ArrayList<WorkHourPolicy>();

			if(swcWorkHours != null) {
				for(int i=0; i<swcWorkHours.length; i++) {
					SwcWorkHour swcWorkHour = swcWorkHours[i];
					int firstDayOfWeek = Integer.parseInt(swcWorkHour.getStartDayOfWeek()); 
					int workingDays = swcWorkHour.getWorkingDays();

					WorkHourPolicy workHourPolicy = new WorkHourPolicy();
					workHourPolicy.setId(swcWorkHour.getObjId());
					workHourPolicy.setValidFrom(new LocalDate(swcWorkHour.getValidFromDate().getTime()));
					if(swcWorkHour.getValidToDate() != null)
						workHourPolicy.setValidTo(new LocalDate(swcWorkHour.getValidToDate().getTime()));
					workHourPolicy.setFirstDayOfWeek(firstDayOfWeek);
					workHourPolicy.setWorkingDays(workingDays);

					WorkHour[] workHours = new WorkHour[7];
					for(int j=firstDayOfWeek; j<firstDayOfWeek+workingDays; j++) {

						int start;
						int end;
						int workTime;
		
						Calendar startCalendar = Calendar.getInstance();
						Calendar endCalendar = Calendar.getInstance();
						int dayOfWeek = j;
		
						if(j > 7)
							dayOfWeek = j % 7;

						switch (dayOfWeek) {
						case Calendar.SUNDAY:
							startCalendar.setTime(swcWorkHour.getSunStartTime());
							endCalendar.setTime(swcWorkHour.getSunEndTime());
							break;
						case Calendar.MONDAY:
							startCalendar.setTime(swcWorkHour.getMonStartTime());
							endCalendar.setTime(swcWorkHour.getMonEndTime());
							break;
						case Calendar.TUESDAY:
							startCalendar.setTime(swcWorkHour.getTueStartTime());
							endCalendar.setTime(swcWorkHour.getTueEndTime());
							break;
						case Calendar.WEDNESDAY:
							startCalendar.setTime(swcWorkHour.getWedStartTime());
							endCalendar.setTime(swcWorkHour.getWedEndTime());
							break;
						case Calendar.THURSDAY:
							startCalendar.setTime(swcWorkHour.getThuStartTime());
							endCalendar.setTime(swcWorkHour.getThuEndTime());
							break;
						case Calendar.FRIDAY:
							startCalendar.setTime(swcWorkHour.getFriStartTime());
							endCalendar.setTime(swcWorkHour.getFriEndTime());
							break;
						case Calendar.SATURDAY:
							startCalendar.setTime(swcWorkHour.getSatStartTime());
							endCalendar.setTime(swcWorkHour.getSatEndTime());
							break;
						default: 
							break;
						}
						start = startCalendar.get(Calendar.HOUR_OF_DAY) * LocalDate.ONE_HOUR + startCalendar.get(Calendar.MINUTE) * LocalDate.ONE_MINUTE;
						end = endCalendar.get(Calendar.HOUR_OF_DAY) * LocalDate.ONE_HOUR + endCalendar.get(Calendar.MINUTE) * LocalDate.ONE_MINUTE;
						workTime = end - start;

						workHours[dayOfWeek-1] = new WorkHour(start, end, workTime);
					}
					workHourPolicy.setWorkHours(workHours);
					workHourPolicyList.add(workHourPolicy);
				}
				WorkHourPolicy[] workHourPolicies = new WorkHourPolicy[workHourPolicyList.size()];
				workHourPolicyList.toArray(workHourPolicies);

				recordList.setRecords(workHourPolicies);
				recordList.setPageSize(pageSize);
				recordList.setTotalPages(totalPages);
				recordList.setCurrentPage(currentPage);
				recordList.setType(InstanceInfoList.TYPE_INFORMATION_INSTANCE_LIST);

				return recordList;
			} else {
				return null;
			}
		 } catch(Exception e) {
			e.printStackTrace();
			return null;			
		}		

	}

	@Override
	public WorkHourPolicy getWorkHourPolicyById(String id) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			SwcWorkHour swcWorkHour = getSwcManager().getWorkhour(cUser.getId(), id, IManager.LEVEL_ALL);
			WorkHourPolicy workHourPolicy = new WorkHourPolicy();

			if(swcWorkHour != null) {
				int firstDayOfWeek = Integer.parseInt(swcWorkHour.getStartDayOfWeek()); 
				int workingDays = swcWorkHour.getWorkingDays();

				workHourPolicy.setId(id);
				workHourPolicy.setValidFrom(new LocalDate(swcWorkHour.getValidFromDate().getTime()));
				if(swcWorkHour.getValidToDate() != null)
					workHourPolicy.setValidTo(new LocalDate(swcWorkHour.getValidToDate().getTime()));
				workHourPolicy.setFirstDayOfWeek(firstDayOfWeek);
				workHourPolicy.setWorkingDays(workingDays);
	
				WorkHour[] workHours = new WorkHour[7];
				for(int j=firstDayOfWeek; j<firstDayOfWeek+workingDays; j++) {

					int start;
					int end;
					int workTime;
	
					Calendar startCalendar = Calendar.getInstance();
					Calendar endCalendar = Calendar.getInstance();
					int dayOfWeek = j;
	
					if(j > 7)
						dayOfWeek = j % 7;
	
					switch (dayOfWeek) {
					case Calendar.SUNDAY:
						startCalendar.setTime(swcWorkHour.getSunStartTime());
						endCalendar.setTime(swcWorkHour.getSunEndTime());
						break;
					case Calendar.MONDAY:
						startCalendar.setTime(swcWorkHour.getMonStartTime());
						endCalendar.setTime(swcWorkHour.getMonEndTime());
						break;
					case Calendar.TUESDAY:
						startCalendar.setTime(swcWorkHour.getTueStartTime());
						endCalendar.setTime(swcWorkHour.getTueEndTime());
						break;
					case Calendar.WEDNESDAY:
						startCalendar.setTime(swcWorkHour.getWedStartTime());
						endCalendar.setTime(swcWorkHour.getWedEndTime());
						break;
					case Calendar.THURSDAY:
						startCalendar.setTime(swcWorkHour.getThuStartTime());
						endCalendar.setTime(swcWorkHour.getThuEndTime());
						break;
					case Calendar.FRIDAY:
						startCalendar.setTime(swcWorkHour.getFriStartTime());
						endCalendar.setTime(swcWorkHour.getFriEndTime());
						break;
					case Calendar.SATURDAY:
						startCalendar.setTime(swcWorkHour.getSatStartTime());
						endCalendar.setTime(swcWorkHour.getSatEndTime());
						break;
					default:
						break;
					}
					start = startCalendar.get(Calendar.HOUR_OF_DAY) * LocalDate.ONE_HOUR + startCalendar.get(Calendar.MINUTE) * LocalDate.ONE_MINUTE;
					end = endCalendar.get(Calendar.HOUR_OF_DAY) * LocalDate.ONE_HOUR + endCalendar.get(Calendar.MINUTE) * LocalDate.ONE_MINUTE;
					workTime = end - start;
	
					workHours[dayOfWeek-1] = new WorkHour(start, end, workTime);
				}
				workHourPolicy.setWorkHours(workHours);
			} else {
				workHourPolicy = new WorkHourPolicy();
			}
			return workHourPolicy;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public void setWorkHourPolicy(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			Map<String, Object> frmEditWorkHour = (Map<String, Object>)requestBody.get("frmEditWorkHour");

			String policyId = (String)requestBody.get("policyId");

			Set<String> keySet = frmEditWorkHour.keySet();
			Iterator<String> itr = keySet.iterator();

			Date datValidFrom = null;
			String selFirstDayOfWeek = null;
			int selWorkingDays = 0;
			List<String> timWorkStart = null;
			List<String> timWorkEnd = null;
			Date startTime = null;
			Date endTime = null;

			SwcWorkHour swcWorkHour = null;
			if(!policyId.equals("")) {
				swcWorkHour = getSwcManager().getWorkhour(userId, policyId, IManager.LEVEL_ALL);
			} else {
				swcWorkHour = new SwcWorkHour();
				swcWorkHour.setCompanyId(companyId);
				swcWorkHour.setType("0");
			}
			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditWorkHour.get(fieldId);
				if(fieldValue instanceof String) {					
					if(fieldId.equals("datValidFrom")) {
						datValidFrom = new Date(LocalDate.convertLocalDateStringToLocalDate((String)frmEditWorkHour.get("datValidFrom")).getTime());
						swcWorkHour.setValidFromDate(datValidFrom);
					} else if(fieldId.equals("selFirstDayOfWeek")) {
						selFirstDayOfWeek = (String)frmEditWorkHour.get("selFirstDayOfWeek");
						swcWorkHour.setStartDayOfWeek(selFirstDayOfWeek);
					} else if(fieldId.equals("selWorkingDays")) {
						//selWorkingDays = Integer.parseInt((String)frmEditWorkHour.get("selWorkingDays"));
						//swcWorkHour.setWorkingDays(selWorkingDays);
					}
				} else if(fieldValue instanceof ArrayList) {
					if(fieldId.equals("timWorkStart")) {
						timWorkStart = (ArrayList<String>)frmEditWorkHour.get("timWorkStart");
						selWorkingDays = 0;
						for(int i=0; i<timWorkStart.size(); i++) {
							if(!timWorkStart.get(i).equals("00:00"))
								selWorkingDays++;
							startTime = LocalDate.convertTimeStringToDate(timWorkStart.get(i));
							if(i==0)swcWorkHour.setSunStartTime(startTime);
							else if(i==1)swcWorkHour.setMonStartTime(startTime);
							else if(i==2)swcWorkHour.setTueStartTime(startTime);
							else if(i==3)swcWorkHour.setWedStartTime(startTime);
							else if(i==4)swcWorkHour.setThuStartTime(startTime);
							else if(i==5)swcWorkHour.setFriStartTime(startTime);
							else if(i==6)swcWorkHour.setSatStartTime(startTime);
						}
						swcWorkHour.setWorkingDays(selWorkingDays);
					} else if(fieldId.equals("timWorkEnd")) {
						timWorkEnd = (ArrayList<String>)frmEditWorkHour.get("timWorkEnd");
						for(int i=0; i<timWorkEnd.size(); i++) {
							endTime = LocalDate.convertTimeStringToDate(timWorkEnd.get(i));
							if(i==0)swcWorkHour.setSunEndTime(endTime);
							else if(i==1)swcWorkHour.setMonEndTime(endTime);
							else if(i==2)swcWorkHour.setTueEndTime(endTime);
							else if(i==3)swcWorkHour.setWedEndTime(endTime);
							else if(i==4)swcWorkHour.setThuEndTime(endTime);
							else if(i==5)swcWorkHour.setFriEndTime(endTime);
							else if(i==6)swcWorkHour.setSatEndTime(endTime);
						}
					}
				}
			}
			getSwcManager().setWorkhour(userId, swcWorkHour, IManager.LEVEL_ALL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeWorkHourPolicy(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String policyId = (String)requestBody.get("policyId");
			if(policyId.equals(""))
				return;
			getSwcManager().removeWorkhour(cUser.getId(), policyId);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}

	@Override
	public RecordList getCompanyEventList(RequestParams params) throws Exception {

		try {
			RecordList recordList = new RecordList();
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			SwcEventDayCond swcEventDayCond = new SwcEventDayCond();
			swcEventDayCond.setCompanyId(companyId);

			long totalCount = getSwcManager().getEventdaySize(userId, swcEventDayCond);

			int pageSize = params.getPageSize();
			if(pageSize == 0) pageSize = 20;

			int currentPage = params.getCurrentPage();
			if(currentPage == 0) currentPage = 1;

			int totalPages = (int)totalCount % pageSize;

			if(totalPages == 0)
				totalPages = (int)totalCount / pageSize;
			else
				totalPages = (int)totalCount / pageSize + 1;

			int result = 0;

			if(params.getPagingAction() != 0) {
				if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXT10) {
					result = (((currentPage - 1) / 10) * 10) + 11;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXTEND) {
					result = totalPages;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREV10) {
					result = ((currentPage - 1) / 10) * 10;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREVEND) {
					result = 1;
				}
				currentPage = result;
			}

			if(previousPageSize != pageSize)
				currentPage = 1;

			previousPageSize = pageSize;

			if((long)((pageSize * (currentPage - 1)) + 1) > totalCount)
				currentPage = 1;

			if (currentPage > 0)
				swcEventDayCond.setPageNo(currentPage-1);

			swcEventDayCond.setPageSize(pageSize);

			swcEventDayCond.setOrders(new Order[]{new Order("startDay", false)});
			SwcEventDay[] swcEventDays = getSwcManager().getEventdays(userId, swcEventDayCond, IManager.LEVEL_ALL);

			if(swcEventDays != null) {
				List<CompanyEvent> companyEventList = new ArrayList<CompanyEvent>();
				for(SwcEventDay swcEventDay : swcEventDays) {
					CompanyEvent companyEvent = new CompanyEvent();
					boolean isHoliDay = swcEventDay.getType().equals(CompanyEvent.EVENT_TYPE_HOLIDAY) ? true : false;
					LocalDate plannedStart = new LocalDate(swcEventDay.getStartDay().getTime());
					LocalDate plannedEnd = new LocalDate(swcEventDay.getEndDay().getTime());
					String id = swcEventDay.getObjId();
					String name = swcEventDay.getName();
					if(swcEventDay.getReltdPerson() != null) {
						List<Community> userList = new ArrayList<Community>();
						String[] reltdUsers = swcEventDay.getReltdPerson().split(";");
						if(reltdUsers != null && reltdUsers.length > 0) {
							for(String reltdUser : reltdUsers) {
								Object obj = communityService.getWorkSpaceById(reltdUser);
								userList.add((Community)obj);
							}
						}
						Community[] relatedUsers = new Community[userList.size()];
						userList.toArray(relatedUsers);
						companyEvent.setRelatedUsers(relatedUsers);
					}
					companyEvent.setId(id);
					companyEvent.setName(name);
					companyEvent.setHoliday(isHoliDay);
					companyEvent.setPlannedStart(plannedStart);
					companyEvent.setPlannedEnd(plannedEnd);
					companyEventList.add(companyEvent);
				}
				CompanyEvent[] companyEvents = new CompanyEvent[companyEventList.size()];
				companyEventList.toArray(companyEvents);

				recordList.setRecords(companyEvents);
				recordList.setPageSize(pageSize);
				recordList.setTotalPages(totalPages);
				recordList.setCurrentPage(currentPage);
				recordList.setType(InstanceInfoList.TYPE_INFORMATION_INSTANCE_LIST);

				return recordList;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;			
		}
	}

	@Override
	public CompanyEvent getCompanyEventById(String id) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			SwcEventDay swcEventDay = getSwcManager().getEventday(userId, id, IManager.LEVEL_ALL);

			if(swcEventDay != null) {
				CompanyEvent companyEvent = new CompanyEvent();
				boolean isHoliDay = swcEventDay.getType().equals(CompanyEvent.EVENT_TYPE_HOLIDAY) ? true : false;
				LocalDate plannedStart = new LocalDate(swcEventDay.getStartDay().getTime());
				LocalDate plannedEnd = new LocalDate(swcEventDay.getEndDay().getTime());
				String name = swcEventDay.getName();
				if(swcEventDay.getReltdPerson() != null) {
					List<Community> userList = new ArrayList<Community>();
					String[] reltdUsers = swcEventDay.getReltdPerson().split(";");
					if(reltdUsers != null && reltdUsers.length > 0) {
						for(String reltdUser : reltdUsers) {
							Object obj = communityService.getWorkSpaceById(reltdUser);
							userList.add((Community)obj);
						}
					}
					Community[] relatedUsers = new Community[userList.size()];
					userList.toArray(relatedUsers);
					companyEvent.setRelatedUsers(relatedUsers);
				}
				companyEvent.setId(id);
				companyEvent.setName(name);
				companyEvent.setHoliday(isHoliDay);
				companyEvent.setPlannedStart(plannedStart);
				companyEvent.setPlannedEnd(plannedEnd);
				return companyEvent;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setCompanyEvent(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try{
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			Map<String, Object> frmEditCompanyEvent = (Map<String, Object>)requestBody.get("frmEditCompanyEvent");
			String eventId = (String)requestBody.get("eventId");

			Set<String> keySet = frmEditCompanyEvent.keySet();
			Iterator<String> itr = keySet.iterator();

			String txtEventName = null;
			Date datStartDate = null;
			Date datEndDate = null;
			List<Map<String, String>> users = null;

			SwcEventDay swcEventDay = null;
			if(!eventId.equals("")) {
				swcEventDay = getSwcManager().getEventday(userId, eventId, IManager.LEVEL_ALL);
			} else {
				swcEventDay = new SwcEventDay();
				swcEventDay.setType(CompanyEvent.EVENT_TYPE_EVENTDAY);
				swcEventDay.setCompanyId(companyId);
			}

			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditCompanyEvent.get(fieldId);
				if(fieldValue instanceof LinkedHashMap) {
					Map<String, Object> valueMap = (Map<String, Object>)fieldValue;
					if(fieldId.equals("usrRelatedUsers"))
						users = (ArrayList<Map<String,String>>)valueMap.get("users");

					if(!CommonUtil.isEmpty(users)) {
						String relatedId = "";
						String symbol = ";";
						for(int i=0; i < users.subList(0, users.size()).size(); i++) {
							Map<String, String> user = users.get(i);
							relatedId += user.get("id") + symbol;
						}
						swcEventDay.setReltdPerson(relatedId);
					}
				} else if(fieldValue instanceof String) {	
					if(fieldId.equals("txtEventName")) {
						txtEventName = (String)frmEditCompanyEvent.get("txtEventName");
						swcEventDay.setName(txtEventName);
					} else if(fieldId.equals("chkIsHoliday")) {
						swcEventDay.setType(CompanyEvent.EVENT_TYPE_HOLIDAY);
					} else if(fieldId.equals("datStartDate")) {
						datStartDate = new LocalDate(LocalDate.convertLocalDateStringToLocalDate((String)frmEditCompanyEvent.get("datStartDate")).getTime());
						swcEventDay.setStartDay(datStartDate);
					} else if(fieldId.equals("datEndDate")) {
						datEndDate = new LocalDate(LocalDate.convertLocalDateStringToLocalDate((String)frmEditCompanyEvent.get("datEndDate")).getTime());
						swcEventDay.setEndDay(datEndDate);
					}
				}
			}
			getSwcManager().setEventday(userId, swcEventDay, IManager.LEVEL_ALL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeCompanyEvent(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String eventId = (String)requestBody.get("eventId");
			if(eventId.equals(""))
				return;
			getSwcManager().removeEventday(cUser.getId(), eventId);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	@Override
	public RecordList getApprovalLineList(RequestParams params) throws Exception {

		try {
			RecordList recordList = new RecordList();
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			AprApprovalLineDefCond approvalLineDefCond = new AprApprovalLineDefCond();
			approvalLineDefCond.setCompanyId(companyId);

			long totalCount = getAprManager().getApprovalLineDefSize(userId, approvalLineDefCond);

			int pageSize = params.getPageSize();
			if(pageSize == 0) pageSize = 20;

			int currentPage = params.getCurrentPage();
			if(currentPage == 0) currentPage = 1;

			int totalPages = (int)totalCount % pageSize;

			if(totalPages == 0)
				totalPages = (int)totalCount / pageSize;
			else
				totalPages = (int)totalCount / pageSize + 1;

			int result = 0;

			if(params.getPagingAction() != 0) {
				if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXT10) {
					result = (((currentPage - 1) / 10) * 10) + 11;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXTEND) {
					result = totalPages;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREV10) {
					result = ((currentPage - 1) / 10) * 10;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREVEND) {
					result = 1;
				}
				currentPage = result;
			}

			if(previousPageSize != pageSize)
				currentPage = 1;

			previousPageSize = pageSize;

			if((long)((pageSize * (currentPage - 1)) + 1) > totalCount)
				currentPage = 1;

			if (currentPage > 0)
				approvalLineDefCond.setPageNo(currentPage-1);

			approvalLineDefCond.setPageSize(pageSize);

			approvalLineDefCond.setOrders(new Order[]{new Order("modificationDate", false)});
			AprApprovalLineDef[] approvalLineDefs = getAprManager().getApprovalLineDefs(userId, approvalLineDefCond, IManager.LEVEL_ALL);

			if(approvalLineDefs != null) {
				List<ApprovalLine> approvalLineList = new ArrayList<ApprovalLine>();
				for(AprApprovalLineDef approvalLineDef : approvalLineDefs) {
					ApprovalLine approvalLine = new ApprovalLine();
					String id = approvalLineDef.getObjId();
					String name = approvalLineDef.getAprLineName();
					String desc = CommonUtil.toNotNull(approvalLineDef.getDescription());
					int approvalLevel = Integer.parseInt(approvalLineDef.getAprLevel());
					approvalLine.setId(id);
					approvalLine.setName(name);
					approvalLine.setDesc(desc);
					approvalLine.setApprovalLevel(approvalLevel);
					AprApprovalDef[] approvalDefs = approvalLineDef.getApprovalDefs();
					if(approvalDefs != null) {
						List<Approval> approvalList = new ArrayList<Approval>();
						for(AprApprovalDef approvalDef : approvalDefs) {
							Approval approval = new Approval();
							approval.setName(approvalDef.getAprName());
							approval.setApproverType(Integer.parseInt(approvalDef.getType()));
							approval.setApprover(ModelConverter.getUserByUserId(approvalDef.getAprPerson()));
							String dueDate = approvalDef.getDueDate();
							int meanTimeDays = 0;
							int meanTimeHours = 0;
							int meanTimeMinutes = 30;
							int daysToMinutes = 60 * 24;
							int hoursToMinutes = 60;
							if(dueDate != null) {
								int meanTime = Integer.parseInt(dueDate);
								meanTimeDays = meanTime / daysToMinutes;
								meanTime = meanTime % daysToMinutes;
								meanTimeHours = meanTime / hoursToMinutes;
								meanTimeMinutes = meanTime % hoursToMinutes;
							}
							approval.setMeanTimeDays(meanTimeDays);
							approval.setMeanTimeHours(meanTimeHours);
							approval.setMeanTimeMinutes(meanTimeMinutes);
							approvalList.add(approval);
						}
						Approval[] approvals = new Approval[approvalList.size()];
						approvalList.toArray(approvals);
						approvalLine.setApprovals(approvals);
					}
					approvalLineList.add(approvalLine);
				}
				ApprovalLine[] approvalLines = new ApprovalLine[approvalLineList.size()];
				approvalLineList.toArray(approvalLines);

				recordList.setRecords(approvalLines);
				recordList.setPageSize(pageSize);
				recordList.setTotalPages(totalPages);
				recordList.setCurrentPage(currentPage);
				recordList.setType(InstanceInfoList.TYPE_INFORMATION_INSTANCE_LIST);

				return recordList;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;			
		}
	}

	@Override
	public ApprovalLine getApprovalLineById(String id) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			AprApprovalLineDef approvalLineDef = getAprManager().getApprovalLineDef(userId, id, IManager.LEVEL_ALL);

			if(approvalLineDef != null) {
				ApprovalLine approvalLine = new ApprovalLine();
				String name = approvalLineDef.getAprLineName();
				String desc = CommonUtil.toNotNull(approvalLineDef.getDescription());
				int approvalLevel = Integer.parseInt(approvalLineDef.getAprLevel());
				approvalLine.setId(id);
				approvalLine.setName(name);
				approvalLine.setDesc(desc);
				approvalLine.setApprovalLevel(approvalLevel);
				AprApprovalDef[] approvalDefs = approvalLineDef.getApprovalDefs();
				if(approvalDefs != null) {
					List<Approval> approvalList = new ArrayList<Approval>();
					for(AprApprovalDef approvalDef : approvalDefs) {
						Approval approval = new Approval();
						approval.setName(approvalDef.getAprName());
						approval.setApproverType(Integer.parseInt(approvalDef.getType()));
						approval.setApprover(ModelConverter.getUserByUserId(approvalDef.getAprPerson()));
						String dueDate = approvalDef.getDueDate();
						int meanTimeDays = 0;
						int meanTimeHours = 0;
						int meanTimeMinutes = 30;
						int daysToMinutes = 60 * 24;
						int hoursToMinutes = 60;
						if(dueDate != null) {
							int meanTime = Integer.parseInt(dueDate);
							meanTimeDays = meanTime / daysToMinutes;
							meanTime = meanTime % daysToMinutes;
							meanTimeHours = meanTime / hoursToMinutes;
							meanTimeMinutes = meanTime % hoursToMinutes;
						}
						approval.setMeanTimeDays(meanTimeDays);
						approval.setMeanTimeHours(meanTimeHours);
						approval.setMeanTimeMinutes(meanTimeMinutes);
						approvalList.add(approval);
					}
					Approval[] approvals = new Approval[approvalList.size()];
					approvalList.toArray(approvals);
					approvalLine.setApprovals(approvals);
				}
				return approvalLine;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setApprovalLine(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

/*		try{
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			Map<String, Object> frmEditCompanyEvent = (Map<String, Object>)requestBody.get("frmEditCompanyEvent");
			String eventId = (String)requestBody.get("lineId");

			Set<String> keySet = frmEditCompanyEvent.keySet();
			Iterator<String> itr = keySet.iterator();

			String txtEventName = null;
			Date datStartDate = null;
			Date datEndDate = null;
			List<Map<String, String>> users = null;

			SwcEventDay swcEventDay = null;
			if(!eventId.equals("")) {
				swcEventDay = getSwcManager().getEventday(userId, eventId, IManager.LEVEL_ALL);
			} else {
				swcEventDay = new SwcEventDay();
				swcEventDay.setType(CompanyEvent.EVENT_TYPE_EVENTDAY);
				swcEventDay.setCompanyId(companyId);
			}

			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditCompanyEvent.get(fieldId);
				if(fieldValue instanceof LinkedHashMap) {
					Map<String, Object> valueMap = (Map<String, Object>)fieldValue;
					if(fieldId.equals("usrRelatedUsers"))
						users = (ArrayList<Map<String,String>>)valueMap.get("users");

					if(!CommonUtil.isEmpty(users)) {
						String relatedId = "";
						String symbol = ";";
						for(int i=0; i < users.subList(0, users.size()).size(); i++) {
							Map<String, String> user = users.get(i);
							relatedId += user.get("id") + symbol;
						}
						swcEventDay.setReltdPerson(relatedId);
					}
				} else if(fieldValue instanceof String) {	
					if(fieldId.equals("txtEventName")) {
						txtEventName = (String)frmEditCompanyEvent.get("txtEventName");
						swcEventDay.setName(txtEventName);
					} else if(fieldId.equals("chkIsHoliday")) {
						swcEventDay.setType(CompanyEvent.EVENT_TYPE_HOLIDAY);
					} else if(fieldId.equals("datStartDate")) {
						datStartDate = new LocalDate(LocalDate.convertLocalDateStringToLocalDate((String)frmEditCompanyEvent.get("datStartDate")).getTime());
						swcEventDay.setStartDay(datStartDate);
					} else if(fieldId.equals("datEndDate")) {
						datEndDate = new LocalDate(LocalDate.convertLocalDateStringToLocalDate((String)frmEditCompanyEvent.get("datEndDate")).getTime());
						swcEventDay.setEndDay(datEndDate);
					}
				}
			}
			getSwcManager().setEventday(userId, swcEventDay, IManager.LEVEL_ALL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	}
	
	@Override
	public void removeApprovalLine(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String lineId = (String)requestBody.get("lineId");
			if(lineId.equals(""))
				return;
			getAprManager().removeApprovalLineDef(cUser.getId(), lineId);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public RecordList getWebServiceList(RequestParams params) throws Exception {

		try {
			RecordList recordList = new RecordList();
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			SwcWebServiceCond swcWebServiceCond = new SwcWebServiceCond();
			swcWebServiceCond.setCompanyId(companyId);

			long totalCount = getSwcManager().getWebServiceSize(userId, swcWebServiceCond);

			int pageSize = params.getPageSize();
			if(pageSize == 0) pageSize = 20;

			int currentPage = params.getCurrentPage();
			if(currentPage == 0) currentPage = 1;

			int totalPages = (int)totalCount % pageSize;

			if(totalPages == 0)
				totalPages = (int)totalCount / pageSize;
			else
				totalPages = (int)totalCount / pageSize + 1;

			int result = 0;

			if(params.getPagingAction() != 0) {
				if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXT10) {
					result = (((currentPage - 1) / 10) * 10) + 11;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXTEND) {
					result = totalPages;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREV10) {
					result = ((currentPage - 1) / 10) * 10;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREVEND) {
					result = 1;
				}
				currentPage = result;
			}

			if(previousPageSize != pageSize)
				currentPage = 1;

			previousPageSize = pageSize;

			if((long)((pageSize * (currentPage - 1)) + 1) > totalCount)
				currentPage = 1;

			if (currentPage > 0)
				swcWebServiceCond.setPageNo(currentPage-1);

			swcWebServiceCond.setPageSize(pageSize);

			//swcWebServiceCond.setOrders(new Order[]{new Order("webServiceName", true)});
			SwcWebService[] swcWebServices = getSwcManager().getWebServices(userId, swcWebServiceCond, IManager.LEVEL_ALL);

			if(swcWebServices != null) {
				List<WebService> webServiceList = new ArrayList<WebService>();
				for(SwcWebService swcWebService : swcWebServices) {
					WebService webService = new WebService();
					String id = swcWebService.getObjId();
					String name = swcWebService.getWebServiceName();
					String desc = swcWebService.getDescription();
					String wsdlUri = swcWebService.getWsdlAddress();
					String port = swcWebService.getPortName();
					String operation = swcWebService.getOperationName();
					webService.setId(id);
					webService.setName(name);
					webService.setDesc(desc);
					webService.setWsdlUri(wsdlUri);
					webService.setPort(port);
					webService.setOperation(operation);
					SwcWebServiceParameter[] swcWebServiceParameters = swcWebService.getSwcWebServiceParameters();
					if(swcWebServiceParameters != null) {
						List<Variable> inputVariableList = new ArrayList<Variable>();
						List<Variable> returnVariableList = new ArrayList<Variable>();
						for(SwcWebServiceParameter swcWebServiceParameter : swcWebServiceParameters) {
							Variable variable = new Variable();
							String type = swcWebServiceParameter.getType();
							if(type.equals("I")) {
								variable.setName(swcWebServiceParameter.getVariableName());
								variable.setElementName(swcWebServiceParameter.getParameterName());
								variable.setElementType(swcWebServiceParameter.getParameterType());
								inputVariableList.add(variable);
							} else if(type.equals("O")) {
								variable.setName(swcWebServiceParameter.getVariableName());
								variable.setElementName(swcWebServiceParameter.getParameterName());
								variable.setElementType(swcWebServiceParameter.getParameterType());
								returnVariableList.add(variable);
							}
						}
						Variable[] inputVariables = new Variable[inputVariableList.size()];
						inputVariableList.toArray(inputVariables);
						webService.setInputVariables(inputVariables);
						Variable[] returnVariables = new Variable[returnVariableList.size()];
						returnVariableList.toArray(returnVariables);
						webService.setReturnVariables(returnVariables);
					}
					webServiceList.add(webService);
				}
				WebService[] webServices = new WebService[webServiceList.size()];
				webServiceList.toArray(webServices);

				recordList.setRecords(webServices);
				recordList.setPageSize(pageSize);
				recordList.setTotalPages(totalPages);
				recordList.setCurrentPage(currentPage);
				recordList.setType(InstanceInfoList.TYPE_INFORMATION_INSTANCE_LIST);

				return recordList;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;			
		}
	}

	@Override
	public WebService getWebServiceById(String id) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			SwcWebService swcWebService = getSwcManager().getWebService(userId, id, IManager.LEVEL_ALL);

			if(swcWebService != null) {
				WebService webService = new WebService();
				String name = swcWebService.getWebServiceName();
				String desc = swcWebService.getDescription();
				String wsdlUri = swcWebService.getWsdlAddress();
				String port = swcWebService.getPortName();
				String operation = swcWebService.getOperationName();
				webService.setId(id);
				webService.setName(name);
				webService.setDesc(desc);
				webService.setWsdlUri(wsdlUri);
				webService.setPort(port);
				webService.setOperation(operation);
				SwcWebServiceParameter[] swcWebServiceParameters = swcWebService.getSwcWebServiceParameters();
				if(swcWebServiceParameters != null) {
					List<Variable> inputVariableList = new ArrayList<Variable>();
					List<Variable> returnVariableList = new ArrayList<Variable>();
					for(SwcWebServiceParameter swcWebServiceParameter : swcWebServiceParameters) {
						Variable variable = new Variable();
						String type = swcWebServiceParameter.getType();
						if(type.equals("I")) {
							variable.setName(swcWebServiceParameter.getVariableName());
							variable.setElementName(swcWebServiceParameter.getParameterName());
							variable.setElementType(swcWebServiceParameter.getParameterType());
							inputVariableList.add(variable);
						} else if(type.equals("O")) {
							variable.setName(swcWebServiceParameter.getVariableName());
							variable.setElementName(swcWebServiceParameter.getParameterName());
							variable.setElementType(swcWebServiceParameter.getParameterType());
							returnVariableList.add(variable);
						}
					}
					Variable[] inputVariables = new Variable[inputVariableList.size()];
					inputVariableList.toArray(inputVariables);
					webService.setInputVariables(inputVariables);
					Variable[] returnVariables = new Variable[returnVariableList.size()];
					returnVariableList.toArray(returnVariables);
					webService.setReturnVariables(returnVariables);
				}
				return webService;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setWebService(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			Map<String, Object> frmEditWebService = (Map<String, Object>)requestBody.get("frmEditWebService");
			String serviceId = (String)requestBody.get("serviceId");

			Set<String> keySet = frmEditWebService.keySet();
			Iterator<String> itr = keySet.iterator();

			String txtWebServiceName = null;
			String txtaWebServiceDesc = null;
			String txtWebServiceWSDL = null;
			String txtWebServiceAddress = null;
			String selWebServicePort = null;
			String selWebServiceOperation = null;
			String txtInputVariableName = null;
			String txtInputElementName = null;
			String txtInputElementType = null;
			String txtReturnVariableName = null;
			String txtReturnElementName = null;
			String txtReturnElementType = null;
			List<String> txtInputVariableNameList = null;
			List<String> txtInputElementNameList = null;
			List<String> txtInputElementTypeList = null;

			SwcWebService swcWebService = null;
			if(!serviceId.equals("")) {
				swcWebService = getSwcManager().getWebService(userId, serviceId, IManager.LEVEL_ALL);
			} else {
				swcWebService = new SwcWebService();
				swcWebService.setCompanyId(companyId);
			}

			List<SwcWebServiceParameter> singleInputWebServiceParameterList = new ArrayList<SwcWebServiceParameter>();
			List<SwcWebServiceParameter> multiInputWebServiceParameterList = new ArrayList<SwcWebServiceParameter>();
			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditWebService.get(fieldId);
				SwcWebServiceParameter swcWebServiceParameter = new SwcWebServiceParameter();
				if(fieldValue instanceof String) {
					if(fieldId.startsWith("txtInput")) {
						if(fieldId.equals("txtInputVariableName")) {
							txtInputVariableName = (String)frmEditWebService.get("txtInputVariableName");
							txtInputElementName = (String)frmEditWebService.get("txtInputElementName");
							txtInputElementType = (String)frmEditWebService.get("txtInputElementType");
							swcWebServiceParameter.setVariableName(txtInputVariableName);
							swcWebServiceParameter.setParameterName(txtInputElementName);
							swcWebServiceParameter.setParameterType(txtInputElementType);
							swcWebServiceParameter.setType("I");
							singleInputWebServiceParameterList.add(swcWebServiceParameter);
						}
					} else if(fieldId.startsWith("txtReturn")) {
						if(fieldId.equals("txtReturnVariableName")) {
							txtReturnVariableName = (String)frmEditWebService.get("txtReturnVariableName");
							txtReturnElementName = (String)frmEditWebService.get("txtReturnElementName");
							txtReturnElementType = (String)frmEditWebService.get("txtReturnElementType");
							swcWebServiceParameter.setVariableName(txtReturnVariableName);
							swcWebServiceParameter.setParameterName(txtReturnElementName);
							swcWebServiceParameter.setParameterType(txtReturnElementType);
							swcWebServiceParameter.setType("O");
							singleInputWebServiceParameterList.add(swcWebServiceParameter);
						}
					} else {
						if(fieldId.equals("txtWebServiceName")) {
							txtWebServiceName = (String)frmEditWebService.get("txtWebServiceName");
							swcWebService.setWebServiceName(txtWebServiceName);
						} else if(fieldId.equals("txtaWebServiceDesc")) {
							txtaWebServiceDesc = (String)frmEditWebService.get("txtaWebServiceDesc");
							swcWebService.setDescription(txtaWebServiceDesc);
						} else if(fieldId.equals("txtWebServiceWSDL")) {
							txtWebServiceWSDL = (String)frmEditWebService.get("txtWebServiceWSDL");
							txtWebServiceAddress = txtWebServiceWSDL.replaceAll("\\?wsdl", "");
							swcWebService.setWsdlAddress(txtWebServiceWSDL);
							swcWebService.setWebServiceAddress(txtWebServiceAddress);
						} else if(fieldId.equals("selWebServicePort")) {
							selWebServicePort = (String)frmEditWebService.get("selWebServicePort");
							swcWebService.setPortName(selWebServicePort);
						} else if(fieldId.equals("selWebServiceOperation")) {
							selWebServiceOperation = (String)frmEditWebService.get("selWebServiceOperation");
							swcWebService.setOperationName(selWebServiceOperation);
						}
					}
				} else if(fieldValue instanceof ArrayList) {
					if(fieldId.equals("txtInputVariableName")) {
						txtInputVariableNameList = (ArrayList<String>)frmEditWebService.get("txtInputVariableName");
						txtInputElementNameList = (ArrayList<String>)frmEditWebService.get("txtInputElementName");
						txtInputElementTypeList = (ArrayList<String>)frmEditWebService.get("txtInputElementType");
						for(int i=0; i<txtInputVariableNameList.size(); i++) {
							SwcWebServiceParameter inputWebServiceParameter = new SwcWebServiceParameter();
							String inputVariableName = txtInputVariableNameList.get(i);
							String inputElementName = txtInputElementNameList.get(i);
							String inputElementType = txtInputElementTypeList.get(i);
							if(!inputVariableName.equals("") && !inputElementName.equals("") && !inputElementType.equals("")) {
								inputWebServiceParameter.setType("I");
								inputWebServiceParameter.setVariableName(inputVariableName);
								inputWebServiceParameter.setParameterName(inputElementName);
								inputWebServiceParameter.setParameterType(inputElementType);
								multiInputWebServiceParameterList.add(inputWebServiceParameter);
							}
						}
						SwcWebServiceParameter[] multiSwcWebServiceParameters = new SwcWebServiceParameter[multiInputWebServiceParameterList.size()];
						multiInputWebServiceParameterList.toArray(multiSwcWebServiceParameters);
						swcWebService.setSwcWebServiceParameters(multiSwcWebServiceParameters);
					}
				}
			}
			if(singleInputWebServiceParameterList.size() > 0) {
				SwcWebServiceParameter[] singleSwcWebServiceParameters = new SwcWebServiceParameter[singleInputWebServiceParameterList.size()];
				singleInputWebServiceParameterList.toArray(singleSwcWebServiceParameters);
				if(singleInputWebServiceParameterList.size() == 1) swcWebService.addWebWebServiceParameter(singleSwcWebServiceParameters[0]);
				else swcWebService.setSwcWebServiceParameters(singleSwcWebServiceParameters);
			}
			getSwcManager().setWebService(userId, swcWebService, IManager.LEVEL_ALL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeWebService(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String serviceId = (String)requestBody.get("serviceId");
			if(serviceId.equals(""))
				return;
			getSwcManager().removeWebService(cUser.getId(), serviceId);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public WSDLDetail getWsdlDetailFromUri(String wsdlUri) throws Exception {

		try {
			WSDLDetail wsdlDetail = new WSDLDetail();
			wsdlDetail.setWsdlUri(wsdlUri);
			List<WSDLPort> wsdlPortList = new ArrayList<WSDLPort>();
			PortType[] portTypes = getSwcManager().getPortTypes(wsdlUri);
			if(portTypes != null) {
				for(PortType portType : portTypes) {
					WSDLPort wsdlPort = new WSDLPort();
					String port = portType.getQName().getLocalPart();
					wsdlPort.setPort(port);
					List<Operation> operationList = portType.getOperations();
					List<WSDLOperation> wsdlOperationList = new ArrayList<WSDLOperation>();
					if(operationList != null && operationList.size() != 0) {
						for(int i=0; i<operationList.size(); i++) {
							Operation operation = operationList.get(i);
							if(operation != null) {
								WSDLOperation wsdlOperation = new WSDLOperation();
								String operationName = operation.getName();
								wsdlOperation.setOperation(operationName);
								List<String> parameterOrdering = operation.getParameterOrdering();
								Input input = operation.getInput();
								Message inputMessage = input.getMessage();
								List<Variable> inputVariableList = new ArrayList<Variable>();
								if(inputMessage != null) {
									Map inputPartsMap = inputMessage.getParts();
									if(inputPartsMap != null) {
										Set<String> inputSet = inputPartsMap.keySet();
									    List<String> inputKeyList = new ArrayList<String>();
									    for(String str : inputSet){
									    	inputKeyList.add(str);
									    }
									    if(parameterOrdering != null) {
											for(String parameter : parameterOrdering){
										    	for(String key : inputKeyList) {
													Variable inputVariable = new Variable();
									    			Part part = (Part)inputPartsMap.get(key);
											    	String inputElementName = part.getName();
											    	String inputElemnetType = "";
											    	QName qName = part.getTypeName();
											    	if(parameter.equals(inputElementName)) {
												    	inputElemnetType = qName.getLocalPart();
												    	inputVariable.setElementName(inputElementName);
												    	inputVariable.setElementType(inputElemnetType);
												    	inputVariableList.add(inputVariable);
											    	}
										    	}
									    	}
									    }
										Variable[] inputVariables = new Variable[inputVariableList.size()];
										inputVariableList.toArray(inputVariables);
										wsdlOperation.setInputVariables(inputVariables);
									}
								}
								Output output = operation.getOutput();
								Message outputMessage = output.getMessage();
								List<Variable> outputVariableList = new ArrayList<Variable>();
								if(outputMessage != null) {
									Map outputPartsMap = outputMessage.getParts();
									if(outputPartsMap != null) {
										Set<String> outputSet = outputPartsMap.keySet();
									    List<String> outputKeyList = new ArrayList<String>();
									    for(String str : outputSet){
									    	outputKeyList.add(str);
									    }
								    	for(String key : outputKeyList) {
											Variable outputVariable = new Variable();
							    			Part part = (Part)outputPartsMap.get(key);
									    	String outputElementName = part.getName();
									    	String outputElemnetType = part.getTypeName().getLocalPart();
									    	outputVariable.setElementName(outputElementName);
									    	outputVariable.setElementType(outputElemnetType);
									    	outputVariableList.add(outputVariable);
								    	}
										Variable[] outputVariables = new Variable[outputVariableList.size()];
										outputVariableList.toArray(outputVariables);
										wsdlOperation.setReturnVariables(outputVariables);
									}
								}
								wsdlOperationList.add(wsdlOperation);
							}
							WSDLOperation[] wsdlOperations = new WSDLOperation[wsdlOperationList.size()];
							wsdlOperationList.toArray(wsdlOperations);
							wsdlPort.setOperations(wsdlOperations);
						}
					}
					wsdlPortList.add(wsdlPort);
				}
				WSDLPort[] wsdlPorts = new WSDLPort[wsdlPortList.size()];
				wsdlPortList.toArray(wsdlPorts);
				wsdlDetail.setPorts(wsdlPorts);
			}
			return wsdlDetail;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RecordList getExternalFormList(RequestParams params) throws Exception {

		try {
			RecordList recordList = new RecordList();
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			SwcExternalFormCond swcExternalFormCond = new SwcExternalFormCond();
			swcExternalFormCond.setCompanyId(companyId);

			long totalCount = getSwcManager().getExternalFormSize(userId, swcExternalFormCond);

			int pageSize = params.getPageSize();
			if(pageSize == 0) pageSize = 20;

			int currentPage = params.getCurrentPage();
			if(currentPage == 0) currentPage = 1;

			int totalPages = (int)totalCount % pageSize;

			if(totalPages == 0)
				totalPages = (int)totalCount / pageSize;
			else
				totalPages = (int)totalCount / pageSize + 1;

			int result = 0;

			if(params.getPagingAction() != 0) {
				if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXT10) {
					result = (((currentPage - 1) / 10) * 10) + 11;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_NEXTEND) {
					result = totalPages;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREV10) {
					result = ((currentPage - 1) / 10) * 10;
				} else if(params.getPagingAction() == RequestParams.PAGING_ACTION_PREVEND) {
					result = 1;
				}
				currentPage = result;
			}

			if(previousPageSize != pageSize)
				currentPage = 1;

			previousPageSize = pageSize;

			if((long)((pageSize * (currentPage - 1)) + 1) > totalCount)
				currentPage = 1;

			if (currentPage > 0)
				swcExternalFormCond.setPageNo(currentPage-1);

			swcExternalFormCond.setPageSize(pageSize);

			//swcExternalFormCond.setOrders(new Order[]{new Order("webAppServiceName", true)});
			SwcExternalForm[] swcExternalForms = getSwcManager().getExternalForms(userId, swcExternalFormCond, IManager.LEVEL_ALL);

			if(swcExternalForms != null) {
				List<ExternalForm> externalFormList = new ArrayList<ExternalForm>();
				for(SwcExternalForm swcExternalForm : swcExternalForms) {
					ExternalForm externalForm = new ExternalForm();
					String id = swcExternalForm.getObjId();
					String name = swcExternalForm.getWebAppServiceName();
					String desc = swcExternalForm.getDescription();
					String url = swcExternalForm.getWebAppServiceUrl();
					String editMethod = swcExternalForm.getModifyMethod();
					String viewMethod = swcExternalForm.getViewMethod();
					externalForm.setId(id);
					externalForm.setName(name);
					externalForm.setDesc(desc);
					externalForm.setUrl(url);
					externalForm.setEditMethod(editMethod);
					externalForm.setViewMethod(viewMethod);
					SwcExternalFormParameter[] swcExternalFormParameters = swcExternalForm.getSwcExternalFormParameters();
					if(swcExternalFormParameters != null) {
						List<Variable> editVariableList = new ArrayList<Variable>();
						List<Variable> viewVariableList = new ArrayList<Variable>();
						List<Variable> returnVariableList = new ArrayList<Variable>();
						for(SwcExternalFormParameter swcExternalFormParameter : swcExternalFormParameters) {
							Variable variable = new Variable();
							String type = swcExternalFormParameter.getType();
							if(type.equals("M")) {
								variable.setName(swcExternalFormParameter.getVariableName());
								variable.setElementName(swcExternalFormParameter.getParameterName());
								variable.setElementType(swcExternalFormParameter.getParameterType());
								editVariableList.add(variable);
							} else if(type.equals("V")) {
								variable.setName(swcExternalFormParameter.getVariableName());
								variable.setElementName(swcExternalFormParameter.getParameterName());
								variable.setElementType(swcExternalFormParameter.getParameterType());
								viewVariableList.add(variable);
							} else if(type.equals("R")) {
								variable.setName(swcExternalFormParameter.getVariableName());
								variable.setElementName(swcExternalFormParameter.getParameterName());
								variable.setElementType(swcExternalFormParameter.getParameterType());
								returnVariableList.add(variable);
							}
						}
						Variable[] editVariables = new Variable[editVariableList.size()];
						editVariableList.toArray(editVariables);
						externalForm.setEditVariables(editVariables);
						Variable[] viewVariables = new Variable[viewVariableList.size()];
						viewVariableList.toArray(viewVariables);
						externalForm.setViewVariables(viewVariables);
						Variable[] returnVariables = new Variable[returnVariableList.size()];
						returnVariableList.toArray(returnVariables);
						externalForm.setReturnVariables(returnVariables);
					}
					externalFormList.add(externalForm);
				}
				ExternalForm[] externalForms = new ExternalForm[externalFormList.size()];
				externalFormList.toArray(externalForms);

				recordList.setRecords(externalForms);
				recordList.setPageSize(pageSize);
				recordList.setTotalPages(totalPages);
				recordList.setCurrentPage(currentPage);
				recordList.setType(InstanceInfoList.TYPE_INFORMATION_INSTANCE_LIST);

				return recordList;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;			
		}
	}

	@Override
	public ExternalForm getExternalFormById(String id) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			SwcExternalForm swcExternalForm = getSwcManager().getExternalForm(userId, id, IManager.LEVEL_ALL);

			if(swcExternalForm != null) {
				ExternalForm externalForm = new ExternalForm();
				String name = swcExternalForm.getWebAppServiceName();
				String desc = swcExternalForm.getDescription();
				String url = swcExternalForm.getWebAppServiceUrl();
				String editMethod = swcExternalForm.getModifyMethod();
				String viewMethod = swcExternalForm.getViewMethod();
				externalForm.setId(id);
				externalForm.setName(name);
				externalForm.setDesc(desc);
				externalForm.setUrl(url);
				externalForm.setEditMethod(editMethod);
				externalForm.setViewMethod(viewMethod);
				SwcExternalFormParameter[] swcExternalFormParameters = swcExternalForm.getSwcExternalFormParameters();
				if(swcExternalFormParameters != null) {
					List<Variable> editVariableList = new ArrayList<Variable>();
					List<Variable> viewVariableList = new ArrayList<Variable>();
					List<Variable> returnVariableList = new ArrayList<Variable>();
					for(SwcExternalFormParameter swcExternalFormParameter : swcExternalFormParameters) {
						Variable variable = new Variable();
						String type = swcExternalFormParameter.getType();
						if(type.equals("M")) {
							variable.setName(swcExternalFormParameter.getVariableName());
							variable.setElementName(swcExternalFormParameter.getParameterName());
							variable.setElementType(swcExternalFormParameter.getParameterType());
							editVariableList.add(variable);
						} else if(type.equals("V")) {
							variable.setName(swcExternalFormParameter.getVariableName());
							variable.setElementName(swcExternalFormParameter.getParameterName());
							variable.setElementType(swcExternalFormParameter.getParameterType());
							viewVariableList.add(variable);
						} else if(type.equals("R")) {
							variable.setName(swcExternalFormParameter.getVariableName());
							variable.setElementName(swcExternalFormParameter.getParameterName());
							variable.setElementType(swcExternalFormParameter.getParameterType());
							returnVariableList.add(variable);
						}
					}
					Variable[] editVariables = new Variable[editVariableList.size()];
					editVariableList.toArray(editVariables);
					externalForm.setEditVariables(editVariables);
					Variable[] viewVariables = new Variable[viewVariableList.size()];
					viewVariableList.toArray(viewVariables);
					externalForm.setViewVariables(viewVariables);
					Variable[] returnVariables = new Variable[returnVariableList.size()];
					returnVariableList.toArray(returnVariables);
					externalForm.setReturnVariables(returnVariables);
				}
				return externalForm;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;			
		}		
	}

	@Override
	public void setExternalForm(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();

			Map<String, Object> frmEditExternalForm = (Map<String, Object>)requestBody.get("frmEditExternalForm");
			String formId = (String)requestBody.get("formId");

			Set<String> keySet = frmEditExternalForm.keySet();
			Iterator<String> itr = keySet.iterator();

			String txtExternalFormName = null;
			String txtExternalFormDesc = null;
			String txtExternalFormURL = null;
			String txtEditMethod = null;
			String txtViewMethod = null;

			String txtInputVariableName = null;
			String txtInputElementName = null;
			String txtInputElementType = null;
			String txtReturnVariableName = null;
			String txtReturnElementName = null;
			String txtReturnElementType = null;
			List<String> txtInputVariableNameList = null;
			List<String> txtInputElementNameList = null;
			List<String> txtInputElementTypeList = null;

			SwcExternalForm swcExternalForm = null;
			if(!formId.equals("")) {
				swcExternalForm = getSwcManager().getExternalForm(userId, formId, IManager.LEVEL_ALL);
			} else {
				swcExternalForm = new SwcExternalForm();
				swcExternalForm.setCompanyId(companyId);
			}

			List<SwcWebServiceParameter> singleInputWebServiceParameterList = new ArrayList<SwcWebServiceParameter>();
			List<SwcWebServiceParameter> multiInputWebServiceParameterList = new ArrayList<SwcWebServiceParameter>();
			
			/*while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditExternalForm.get(fieldId);
				SwcWebServiceParameter swcWebServiceParameter = new SwcWebServiceParameter();
				if(fieldValue instanceof String) {
					if(fieldId.startsWith("txtInput")) {
						if(fieldId.equals("txtInputVariableName")) {
							txtInputVariableName = (String)frmEditWebService.get("txtInputVariableName");
							txtInputElementName = (String)frmEditWebService.get("txtInputElementName");
							txtInputElementType = (String)frmEditWebService.get("txtInputElementType");
							swcWebServiceParameter.setVariableName(txtInputVariableName);
							swcWebServiceParameter.setParameterName(txtInputElementName);
							swcWebServiceParameter.setParameterType(txtInputElementType);
							swcWebServiceParameter.setType("I");
							singleInputWebServiceParameterList.add(swcWebServiceParameter);
						}
					} else if(fieldId.startsWith("txtReturn")) {
						if(fieldId.equals("txtReturnVariableName")) {
							txtReturnVariableName = (String)frmEditWebService.get("txtReturnVariableName");
							txtReturnElementName = (String)frmEditWebService.get("txtReturnElementName");
							txtReturnElementType = (String)frmEditWebService.get("txtReturnElementType");
							swcWebServiceParameter.setVariableName(txtReturnVariableName);
							swcWebServiceParameter.setParameterName(txtReturnElementName);
							swcWebServiceParameter.setParameterType(txtReturnElementType);
							swcWebServiceParameter.setType("O");
							singleInputWebServiceParameterList.add(swcWebServiceParameter);
						}
					} else {
						if(fieldId.equals("txtWebServiceName")) {
							txtWebServiceName = (String)frmEditWebService.get("txtWebServiceName");
							swcWebService.setWebServiceName(txtWebServiceName);
						} else if(fieldId.equals("txtaWebServiceDesc")) {
							txtaWebServiceDesc = (String)frmEditWebService.get("txtaWebServiceDesc");
							swcWebService.setDescription(txtaWebServiceDesc);
						} else if(fieldId.equals("txtWebServiceWSDL")) {
							txtWebServiceWSDL = (String)frmEditWebService.get("txtWebServiceWSDL");
							txtWebServiceAddress = txtWebServiceWSDL.replaceAll("\\?wsdl", "");
							swcWebService.setWsdlAddress(txtWebServiceWSDL);
							swcWebService.setWebServiceAddress(txtWebServiceAddress);
						} else if(fieldId.equals("selWebServicePort")) {
							selWebServicePort = (String)frmEditWebService.get("selWebServicePort");
							swcWebService.setPortName(selWebServicePort);
						} else if(fieldId.equals("selWebServiceOperation")) {
							selWebServiceOperation = (String)frmEditWebService.get("selWebServiceOperation");
							swcWebService.setOperationName(selWebServiceOperation);
						}
					}
				} else if(fieldValue instanceof ArrayList) {
					if(fieldId.equals("txtInputVariableName")) {
						txtInputVariableNameList = (ArrayList<String>)frmEditWebService.get("txtInputVariableName");
						txtInputElementNameList = (ArrayList<String>)frmEditWebService.get("txtInputElementName");
						txtInputElementTypeList = (ArrayList<String>)frmEditWebService.get("txtInputElementType");
						for(int i=0; i<txtInputVariableNameList.size(); i++) {
							SwcWebServiceParameter inputWebServiceParameter = new SwcWebServiceParameter();
							String inputVariableName = txtInputVariableNameList.get(i);
							String inputElementName = txtInputElementNameList.get(i);
							String inputElementType = txtInputElementTypeList.get(i);
							if(!inputVariableName.equals("") && !inputElementName.equals("") && !inputElementType.equals("")) {
								inputWebServiceParameter.setType("I");
								inputWebServiceParameter.setVariableName(inputVariableName);
								inputWebServiceParameter.setParameterName(inputElementName);
								inputWebServiceParameter.setParameterType(inputElementType);
								multiInputWebServiceParameterList.add(inputWebServiceParameter);
							}
						}
						SwcWebServiceParameter[] multiSwcWebServiceParameters = new SwcWebServiceParameter[multiInputWebServiceParameterList.size()];
						multiInputWebServiceParameterList.toArray(multiSwcWebServiceParameters);
						swcWebService.setSwcWebServiceParameters(multiSwcWebServiceParameters);
					}
				}
			}
			if(singleInputWebServiceParameterList.size() > 0) {
				SwcWebServiceParameter[] singleSwcWebServiceParameters = new SwcWebServiceParameter[singleInputWebServiceParameterList.size()];
				singleInputWebServiceParameterList.toArray(singleSwcWebServiceParameters);
				if(singleInputWebServiceParameterList.size() == 1) swcWebService.addWebWebServiceParameter(singleSwcWebServiceParameters[0]);
				else swcWebService.setSwcWebServiceParameters(singleSwcWebServiceParameters);
			}
			getSwcManager().setWebService(userId, swcWebService, IManager.LEVEL_ALL);*/
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeExternalForm(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String formId = (String)requestBody.get("formId");
			if(formId.equals(""))
				return;
			getSwcManager().removeExternalForm(cUser.getId(), formId);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMember(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try{
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();
			Map<String, Object> frmEditMember = (Map<String, Object>)requestBody.get("frmEditMember");

			String setUserId = (String)requestBody.get("userId");

			Set<String> keySet = frmEditMember.keySet();
			Iterator<String> itr = keySet.iterator();

			String hdnDepartmentId = null;
			String txtMemberName = null;
			String txtMemberId = null;
			String pasMemberPassword = null;
			String txtMemberEmployeeId = null;
			String txtMemberPosition = null;
			String selMemberRole = null;
			String selMemberUserLevel = null;
			String selMemberLocale = null;
			String selMemberTimeZone = null;
			String txtMemberPhoneNo = null;
			String txtMemberCellPhoneNo = null;

			SwoUser swoUser = null;
			if(!setUserId.equals("")) {
				swoUser = getSwoManager().getUser(userId, setUserId, IManager.LEVEL_ALL);
				if(swoUser.getDomainId() == null || swoUser.getDomainId() == "")
					swoUser.setDomainId("frm_user_SYSTEM");
				if(swoUser.getRetiree() == null || swoUser.getRetiree() == "")
					swoUser.setRetiree("N");
			} else {
				swoUser = new SwoUser();
				swoUser.setCompanyId(companyId);
				swoUser.setType("BASIC");
				swoUser.setDomainId("frm_user_SYSTEM");
				swoUser.setRetiree("N");
			}

			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditMember.get(fieldId);
				if(fieldValue instanceof String) {
					if(fieldId.equals("hdnDepartmentId")) {
						hdnDepartmentId = (String)frmEditMember.get("hdnDepartmentId");
						swoUser.setDeptId(hdnDepartmentId);
					} else if(fieldId.equals("txtMemberName")) {
						txtMemberName = (String)frmEditMember.get("txtMemberName");
						swoUser.setName(txtMemberName);
					} else if(fieldId.equals("txtMemberId")) {
						txtMemberId = (String)frmEditMember.get("txtMemberId");
						swoUser.setId(txtMemberId);
						swoUser.setEmail(txtMemberId);
					} else if(fieldId.equals("pasMemberPassword")) {
						pasMemberPassword = (String)frmEditMember.get("pasMemberPassword");
						//pasMemberPassword = DigestUtils.md5Hex(pasMemberPassword);
						swoUser.setPassword(pasMemberPassword);
					} else if(fieldId.equals("txtMemberEmployeeId")) {
						txtMemberEmployeeId = (String)frmEditMember.get("txtMemberEmployeeId");
						swoUser.setEmpNo(txtMemberEmployeeId);
					} else if(fieldId.equals("txtMemberPosition")) {
						txtMemberPosition = (String)frmEditMember.get("txtMemberPosition");
						swoUser.setPosition(txtMemberPosition);
					} else if(fieldId.equals("selMemberRole")) {
						selMemberRole = (String)frmEditMember.get("selMemberRole");
						swoUser.setRoleId(selMemberRole.equals(User.USER_ROLE_LEADER) ? "DEPT LEADER" : "DEPT MEMBER");
					} else if(fieldId.equals("selMemberUserLevel")) {
						selMemberUserLevel = (String)frmEditMember.get("selMemberUserLevel");
						swoUser.setAuthId(selMemberUserLevel.equals(User.USER_LEVEL_EXTERNAL_USER) ? "EXTERNALUSER" : selMemberUserLevel.equals(User.USER_LEVEL_INTERNAL_USER) ? "USER" : selMemberUserLevel.equals(User.USER_LEVEL_AMINISTRATOR) ? "ADMINISTRATOR" : "OPERATOR");
					} else if(fieldId.equals("selMemberLocale")) {
						selMemberLocale = (String)frmEditMember.get("selMemberLocale");
						swoUser.setLocale(selMemberLocale);
					} else if(fieldId.equals("selMemberTimeZone")) {
						selMemberTimeZone = (String)frmEditMember.get("selMemberTimeZone");
						swoUser.setTimeZone(selMemberTimeZone);
					} else if(fieldId.equals("txtMemberPhoneNo")) {
						txtMemberPhoneNo = (String)frmEditMember.get("txtMemberPhoneNo");
						swoUser.setExtensionNo(txtMemberPhoneNo);
					} else if(fieldId.equals("txtMemberCellPhoneNo")) {
						txtMemberCellPhoneNo = (String)frmEditMember.get("txtMemberCellPhoneNo");
						swoUser.setMobileNo(txtMemberCellPhoneNo);
					}
				}
			}

			getSwoManager().setUser(userId, swoUser, IManager.LEVEL_ALL);
			if(!setUserId.equals(""))
				getSwoManager().getUserExtend(userId, swoUser.getId(), false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeMember(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String userId = (String)requestBody.get("userId");
			if(userId.equals(""))
				return;
			getSwoManager().removeUser(cUser.getId(), userId);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void setDepartment(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try{
			User cUser = SmartUtil.getCurrentUser();
			String userId = cUser.getId();
			String companyId = cUser.getCompanyId();
			Map<String, Object> frmEditDepartment = (Map<String, Object>)requestBody.get("frmEditDepartment");

			String departmentId = (String)requestBody.get("departmentId");

			Set<String> keySet = frmEditDepartment.keySet();
			Iterator<String> itr = keySet.iterator();

			String hdnParentId = null;
			String txtDepartmentName = null;

			SwoDepartment swoDepartment = null;
			if(!departmentId.equals("")) {
				swoDepartment = getSwoManager().getDepartment(userId, departmentId, IManager.LEVEL_ALL);
			} else {
				swoDepartment = new SwoDepartment();
				swoDepartment.setCompanyId(companyId);
				swoDepartment.setType("BASIC");
				swoDepartment.setDomainId("frm_dept_SYSTEM");
			}

			while (itr.hasNext()) {
				String fieldId = (String)itr.next();
				Object fieldValue = frmEditDepartment.get(fieldId);
				if(fieldValue instanceof String) {
					if(fieldId.equals("hdnParentId")) {
						hdnParentId = (String)frmEditDepartment.get("hdnParentId");
						swoDepartment.setParentId(hdnParentId);
					} else if(fieldId.equals("txtDepartmentName")) {
						txtDepartmentName = (String)frmEditDepartment.get("txtDepartmentName");
						swoDepartment.setName(txtDepartmentName);
					}
				}
			}

			getSwoManager().setDepartment(userId, swoDepartment, IManager.LEVEL_ALL);
			if(!departmentId.equals(""))
				getSwoManager().getDepartmentExtend(userId, departmentId, false);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void removeDepartment(Map<String, Object> requestBody, HttpServletRequest request) throws Exception {

		try {
			User cUser = SmartUtil.getCurrentUser();
			String departmentId = (String)requestBody.get("departmentId");
			if(departmentId.equals(""))
				return;
			getSwoManager().removeDepartment(cUser.getId(), departmentId);
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}

	@Override
	public void checkIdDuplication(HttpServletRequest request) throws Exception {

		try {
			boolean isExistId = getSwoManager().isExistId(request.getParameter("userId"));
			if(isExistId) throw new DuplicateKeyException("duplicateKeyException");
		} catch(Exception e) {
			throw new DuplicateKeyException("duplicateKeyException");
		}
	}

}