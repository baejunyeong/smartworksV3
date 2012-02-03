ALTER TABLE SWDataRef alter column refRecordId varchar(4000);

-- TO-DO : user_field �߰� �� �÷� ���� 4000���� ����

-- SWOrgUser locale, timeZone column add
ALTER TABLE SWOrgUser add locale varchar(20);
ALTER TABLE SWOrgUser add timeZone varchar(20);

ALTER TABLE prcprcinst add prcPackageId varchar(100);
ALTER TABLE prcprcinst add prcType varchar(100);

ALTER TABLE tsktask add isStartActivity varchar(10);
ALTER TABLE tsktask add tskFromRefType varchar(50);
ALTER TABLE tsktask add tskFromRefId varchar(100);

-- TO-DO : mode -> authmode 

-- TO-DO : tsktask : tskWorkspaceId, prcprcinst : prcWorkspaceId

-- TO-DO : ���̺� �� �� �÷��� ���� lnklist, lnklistextprop, lnklistitem, lnkmap, lnkmapextprop, lnkvalue, lnkvalueextprop �� ��� lnk�� col�� ����

-- workspaceid column add
ALTER TABLE TskTask add tskWorkspaceId varchar(100);
ALTER TABLE PrcprcInst add prcWorkspaceId varchar(100);

-- �ڷ�� ���̺� �� ����
sp_rename dt_e2f7c08e0375498499b9a303ef212f53, SWAttachment

-- �ڷ�� domain ����
update swdomain set id='frm_attachment_SYSTEM', formid='frm_attachment_SYSTEM', tblname = 'SWAttachment' where formid = 'frm_148366628fb24edd976940398ba0d8d0'

-- �ڷ�� domainfield ����
update swdomainfield set domainid = 'frm_attachment_SYSTEM' where domainid= 'md_dcab3e031cf74f8a89ed0c9bbdafb0cd'
update swdomainfield set id = 'attachment_owneruser', tablecolname = 'owneruser' where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c3'
update swdomainfield set id = 'attachment_ownerdept', tablecolname = 'ownerdept'  where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c2'
update swdomainfield set id = 'attachment_content', tablecolname = 'content'  where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c4'
update swdomainfield set id = 'attachment_searchword', tablecolname = 'searchword'  where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c1'
update swdomainfield set id = 'attachment_title', tablecolname = 'title'  where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c0'
update swdomainfield set id = 'attachment_filegroupId', tablecolname = 'filegroupid'  where domainid= 'frm_attachment_SYSTEM' and tablecolname = 'c5'

update swform set formid = 'frm_attachment_SYSTEM', content = '<form id="frm_attachment_SYSTEM" version="1" name="�ڷ��">
	<children>
		<formEntity id="0" name="����" systemType="string" required="true" system="false">
			<children/>
			<format type="textInput" viewingType="textInput"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="190" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
		<formEntity id="1" name="�˻���" systemType="string" required="true" system="false">
			<children/>
			<format type="textInput" viewingType="textInput"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="190" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
		<formEntity id="2" name="�����μ�" systemType="string" required="true" system="false">
			<children/>
			<format type="refFormField" viewingType="refFormField">
				<refForm id="frm_dept_SYSTEM" ver="0">
					<name>null</name>
					<category id="null">null</category>
					<field id="10">null</field>
				</refForm>
			</format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="190" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
		<formEntity id="3" name="���������" systemType="string" required="true" system="false">
			<children/>
			<format type="userField" viewingType="userField"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="130" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="false"/>
		</formEntity>
		<formEntity id="4" name="����" systemType="text" required="false" system="false">
			<children/>
			<format type="richEditor" viewingType="richEditor"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="500" height="388" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
		<formEntity id="5" name="÷������" systemType="string" required="true" system="false">
			<children/>
			<format type="fileField" viewingType="fileField"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="275" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
	</children>
	<mappingForms/>
	<layout type="grid_layout">
		<columns>
			<gridColumn size="358.5" labelSize="80"/>
			<gridColumn size="358.5" labelSize="80"/>
		</columns>
		<gridRow size="30">
			<gridCell size="358.5" span="2" fieldId="0"/>
		</gridRow>
		<gridRow size="30">
			<gridCell size="358.5" span="2" fieldId="1"/>
		</gridRow>
		<gridRow size="30">
			<gridCell size="358.5" span="1" fieldId="2"/>
			<gridCell size="358.5" span="1" fieldId="3"/>
		</gridRow>
		<gridRow size="395.5">
			<gridCell size="358.5" span="2" fieldId="4"/>
		</gridRow>
		<gridRow size="30">
			<gridCell size="358.5" span="2" fieldId="5"/>
		</gridRow>
	</layout>
	<graphic width="760" height="605" currentEntityNum="6" currentMappingNum="0">
		<space head="20" left="20" right="20" top="20" bottom="10"/>
	</graphic>
</form>'
where formid = 'frm_148366628fb24edd976940398ba0d8d0'


-- �޸� ���̺� �� ����
sp_rename dt_0d13bec72fcc43bca079eeb927bf1cc6, SWMemo

-- �޸� package ����
update swpackage set name = '�޸�' where packageid = 'pkg_d391d4cd01864b2cada59ab5a9b12cd5'

-- �޸� domain ����
update swdomain set id='frm_memo_SYSTEM', formid='frm_memo_SYSTEM', formName = '�޸�', tblname = 'SWMemo' where formid = 'frm_9d4df59b25694c8ea13e07e0f0fb2579'

-- �޸� domainfield ����
update swdomainfield set domainid = 'frm_memo_SYSTEM' where domainid= 'md_44a75bf293eb4e4a88c18373929b5a56'
update swdomainfield set id = 'memo_title', tablecolname = 'title' where domainid= 'frm_memo_SYSTEM' and tablecolname = 'c12'
update swdomainfield set id = 'memo_content', tablecolname = 'content'  where domainid= 'frm_memo_SYSTEM' and tablecolname = 'c4'

update swform set formid = 'frm_memo_SYSTEM', name = '�޸�', content = '<form id="frm_memo_SYSTEM" version="1" name="�޸�">
	<children>
		<formEntity id="4" name="����" systemType="text" required="true" system="false">
			<children/>
			<format type="richEditor" viewingType="richEditor"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="500" height="604" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
		<formEntity id="12" name="����" systemType="string" required="true" system="false">
			<children/>
			<format type="textInput" viewingType="textInput"></format>
			<graphic hidden="false" readOnly="false" labelWidth="80" contentWidth="190" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true"/>
		</formEntity>
	</children>
	<mappingForms/>
	<layout type="grid_layout">
		<columns>
			<gridColumn size="358.5" labelSize="80"/>
			<gridColumn size="358.5" labelSize="80"/>
		</columns>
		<gridRow size="30">
			<gridCell size="0" span="2" fieldId="12"/>
		</gridRow>
		<gridRow size="611">
			<gridCell size="358.5" span="2" fieldId="4"/>
		</gridRow>
	</layout>
	<graphic width="760" height="735" currentEntityNum="18" currentMappingNum="0">
		<space head="20" left="20" right="20" top="20" bottom="20"/>
	</graphic>
</form>'
where formid = 'frm_148366628fb24edd976940398ba0d8d0'

-- �̺�Ʈ ���̺� �� ����
sp_rename dt_1326675664565, SWEvent

-- �̺�Ʈ domain ����
update swdomain set id='frm_event_SYSTEM', formid='frm_event_SYSTEM', tblname = 'SWEvent' where formid = 'frm_a8b6f52fdd2f4cf6a4f7f3f9f175f4d3'

-- �̺�Ʈ domainfield ����

update swdomainfield set domainid = 'frm_event_SYSTEM' where domainid= 'md_8d94e6225bf74312a22fe9ba2ac98124'
update swdomainfield set id = 'event_content', tablecolname = 'content' where domainid= 'frm_event_SYSTEM' and tablecolname = 'c6'
update swdomainfield set id = 'event_name', tablecolname = 'name'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c0'
update swdomainfield set id = 'event_start', tablecolname = 'startdate'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c1'
update swdomainfield set id = 'event_end', tablecolname = 'enddate'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c2'
update swdomainfield set id = 'event_alarm', tablecolname = 'alarm'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c7'
update swdomainfield set id = 'event_related_users', tablecolname = 'relatedusers'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c5'
update swdomainfield set id = 'event_place', tablecolname = 'place'  where domainid= 'frm_event_SYSTEM' and tablecolname = 'c4'

update swform set formid = 'frm_event_SYSTEM', content = '<form id="frm_event_SYSTEM" version="1" name="�̺�Ʈ">
  <children>
    <formEntity id="0" name="�̺�Ʈ�̸�" systemType="string" required="true" system="false">
      <children/>
      <format type="textInput" viewingType="textInput"></format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="347" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="1" name="��������" systemType="datetime" required="true" system="false">
      <children/>
      <format type="dateTimeChooser" viewingType="dateTimeChooser">
        <date yearUse="false" sunNotUse="false" monNotUse="false" tueNotUse="false" wedNotUse="false" thuNotUse="false" friNotUse="false" satNotUse="false"/>
      </format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="237" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="2" name="��������" systemType="datetime" required="false" system="false">
      <children/>
      <format type="dateTimeChooser" viewingType="dateTimeChooser">
        <date yearUse="false" sunNotUse="false" monNotUse="false" tueNotUse="false" wedNotUse="false" thuNotUse="false" friNotUse="false" satNotUse="false"/>
      </format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="237" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="4" name="���" systemType="string" required="false" system="false">
      <children/>
      <format type="textInput" viewingType="textInput"></format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="347" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="5" name="������" systemType="string" required="false" system="false">
      <children/>
      <format type="userField" viewingType="userField"></format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="237" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="true"/>
    </formEntity>
    <formEntity id="6" name="�̺�Ʈ����" systemType="text" required="false" system="false">
      <children/>
      <format type="richEditor" viewingType="richEditor"></format>
      <graphic hidden="false" readOnly="false" labelWidth="146" contentWidth="915" height="259" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="7" name="�̸��˸�" systemType="string" required="false" system="false">
      <children/>
      <format type="comboBox" viewingType="comboBox">
        <list type="" refCodeCategoryId="null" refCodeCategoryName="null" listType="static">
          <staticItems>
            <staticItem>��������</staticItem>
            <staticItem>����</staticItem>
            <staticItem>5����</staticItem>
            <staticItem>10����</staticItem>
            <staticItem>15����</staticItem>
            <staticItem>30����</staticItem>
            <staticItem>�ѽð���</staticItem>
            <staticItem>�Ϸ���</staticItem>
          </staticItems>
        </list>
      </format>
      <graphic hidden="false" readOnly="false" labelWidth="79" contentWidth="189" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
  </children>
  <mappingForms/>
  <mappingServices/>
  <layout type="grid_layout">
    <columns>
      <gridColumn size="460.60179500795493" labelSize="100.73022128896787"/>
      <gridColumn size="460.60179500795493" labelSize="100.73022128896787"/>
      <gridColumn size="459.9547634680662" labelSize="79.67565821353577"/>
    </columns>
    <gridRow size="30">
      <gridCell size="692.5202713616541" span="3" rowSpan="1" gridColumnIndex="0" fieldId="0"/>
    </gridRow>
    <gridRow size="30">
      <gridCell size="692.5202713616541" span="1" rowSpan="1" gridColumnIndex="0" fieldId="1"/>
      <gridCell size="692.5202713616541" span="1" rowSpan="1" gridColumnIndex="1" fieldId="2"/>
      <gridCell size="99.59457276691973" span="1" rowSpan="1" gridColumnIndex="2" fieldId="7"/>
    </gridRow>
    <gridRow size="30">
      <gridCell size="692.5202713616541" span="3" rowSpan="1" gridColumnIndex="0" fieldId="4"/>
    </gridRow>
    <gridRow size="30">
      <gridCell size="692.5202713616541" span="3" rowSpan="1" gridColumnIndex="0" fieldId="5"/>
    </gridRow>
    <gridRow size="266">
      <gridCell size="692.5202713616541" span="3" rowSpan="1" gridColumnIndex="0" fieldId="6"/>
    </gridRow>
  </layout>
  <graphic width="1399" height="540" currentEntityNum="8" currentMappingNum="0">
    <space/>
  </graphic>
</form>'
where formid = 'frm_a8b6f52fdd2f4cf6a4f7f3f9f175f4d3'


-- �������� ���̺� �� ����
sp_rename dt_1326673665416, SWNotice

-- �������� domain ����
update swdomain set id='frm_notice_SYSTEM', formid='frm_notice_SYSTEM', tblname = 'SWNotice' where formid = 'frm_e5ebdd49311a4007a1abfe831ff68e64'

-- �������� domainfield ����

update swdomainfield set domainid = 'frm_notice_SYSTEM' where domainid= 'md_4d010542aabc45ff98e2f362026e71e0'
update swdomainfield set id = 'notice_filegroupid', tablecolname = 'filegroupid' where domainid= 'frm_notice_SYSTEM' and tablecolname = 'c2'
update swdomainfield set id = 'notice_title', tablecolname = 'title'  where domainid= 'frm_notice_SYSTEM' and tablecolname = 'c0'
update swdomainfield set id = 'notice_content', tablecolname = 'content'  where domainid= 'frm_notice_SYSTEM' and tablecolname = 'c1'

update swform set formid = 'frm_event_SYSTEM', content = '<form id="frm_notice_SYSTEM" version="1" name="��������">
  <children>
    <formEntity id="0" name="����" systemType="string" required="true" system="false">
      <children/>
      <format type="textInput" viewingType="textInput"></format>
      <graphic hidden="false" readOnly="false" labelWidth="113" contentWidth="269" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="1" name="����" systemType="text" required="true" system="false">
      <children/>
      <format type="richEditor" viewingType="richEditor"></format>
      <graphic hidden="false" readOnly="false" labelWidth="113" contentWidth="709" height="354" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
    <formEntity id="2" name="÷������" systemType="string" required="false" system="false">
      <children/>
      <format type="fileField" viewingType="fileField"></format>
      <graphic hidden="false" readOnly="false" labelWidth="113" contentWidth="390" height="23" cellSize="1" fitWidth="false" verticalScrollPolicy="true" textAlign="left" fitToScreen="false" listEditable="false" multipleUsers="false"/>
    </formEntity>
  </children>
  <mappingForms/>
  <mappingServices/>
  <layout type="grid_layout">
    <columns>
      <gridColumn size="536.5610859728507" labelSize="78.04524886877827"/>
      <gridColumn size="536.5610859728507" labelSize="78.04524886877827"/>
    </columns>
    <gridRow size="30">
      <gridCell size="536.5610859728507" span="2" rowSpan="1" gridColumnIndex="0" fieldId="0"/>
    </gridRow>
    <gridRow size="271">
      <gridCell size="536.5610859728507" span="2" rowSpan="4" gridColumnIndex="0" fieldId="1"/>
    </gridRow>
    <gridRow size="30"/>
    <gridRow size="30"/>
    <gridRow size="30"/>
    <gridRow size="30">
      <gridCell size="536.5610859728507" span="2" rowSpan="1" gridColumnIndex="0" fieldId="2"/>
    </gridRow>
  </layout>
  <graphic width="1080" height="577" currentEntityNum="3" currentMappingNum="0">
    <space/>
  </graphic>
</form>'
where formid = 'frm_e5ebdd49311a4007a1abfe831ff68e64'
