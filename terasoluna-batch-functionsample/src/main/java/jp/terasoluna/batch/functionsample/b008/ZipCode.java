package jp.terasoluna.batch.functionsample.b008;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;

@FileFormat(lineFeedChar = "\r\n", fileEncoding = "Windows-31j")
public class ZipCode {

    @InputFileColumn(columnIndex = 0)
    private String groupCode = null;

    @InputFileColumn(columnIndex = 1, columnEncloseChar = '\"')
    private String oldZipCode = null;

    @InputFileColumn(columnIndex = 2, columnEncloseChar = '\"')
    private String zipCode = null;

    @InputFileColumn(columnIndex = 3, columnEncloseChar = '\"')
    private String adminDivisionsKana = null;

    @InputFileColumn(columnIndex = 4, columnEncloseChar = '\"')
    private String municipalDistrictKana = null;

    @InputFileColumn(columnIndex = 5, columnEncloseChar = '\"')
    private String townRegionKana = null;

    @InputFileColumn(columnIndex = 6, columnEncloseChar = '\"')
    private String adminDivisions = null;

    @InputFileColumn(columnIndex = 7, columnEncloseChar = '\"')
    private String municipalDistrict = null;

    @InputFileColumn(columnIndex = 8, columnEncloseChar = '\"')
    private String townRegion = null;

    @InputFileColumn(columnIndex = 9)
    private String townRegionFlag1 = null;

    @InputFileColumn(columnIndex = 10)
    private String townRegionFlag2 = null;

    @InputFileColumn(columnIndex = 11)
    private String townRegionFlag3 = null;

    @InputFileColumn(columnIndex = 12)
    private String townRegionFlag4 = null;

    @InputFileColumn(columnIndex = 13)
    private String updateFlag = null;

    @InputFileColumn(columnIndex = 14)
    private String changeFlag = null;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getOldZipCode() {
        return oldZipCode;
    }

    public void setOldZipCode(String oldZipCode) {
        this.oldZipCode = oldZipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAdminDivisionsKana() {
        return adminDivisionsKana;
    }

    public void setAdminDivisionsKana(String adminDivisionsKana) {
        this.adminDivisionsKana = adminDivisionsKana;
    }

    public String getMunicipalDistrictKana() {
        return municipalDistrictKana;
    }

    public void setMunicipalDistrictKana(String municipalDistrictKana) {
        this.municipalDistrictKana = municipalDistrictKana;
    }

    public String getTownRegionKana() {
        return townRegionKana;
    }

    public void setTownRegionKana(String townRegionKana) {
        this.townRegionKana = townRegionKana;
    }

    public String getAdminDivisions() {
        return adminDivisions;
    }

    public void setAdminDivisions(String adminDivisions) {
        this.adminDivisions = adminDivisions;
    }

    public String getMunicipalDistrict() {
        return municipalDistrict;
    }

    public void setMunicipalDistrict(String municipalDistrict) {
        this.municipalDistrict = municipalDistrict;
    }

    public String getTownRegion() {
        return townRegion;
    }

    public void setTownRegion(String townRegion) {
        this.townRegion = townRegion;
    }

    public String getTownRegionFlag1() {
        return townRegionFlag1;
    }

    public void setTownRegionFlag1(String townRegionFlag1) {
        this.townRegionFlag1 = townRegionFlag1;
    }

    public String getTownRegionFlag2() {
        return townRegionFlag2;
    }

    public void setTownRegionFlag2(String townRegionFlag2) {
        this.townRegionFlag2 = townRegionFlag2;
    }

    public String getTownRegionFlag3() {
        return townRegionFlag3;
    }

    public void setTownRegionFlag3(String townRegionFlag3) {
        this.townRegionFlag3 = townRegionFlag3;
    }

    public String getTownRegionFlag4() {
        return townRegionFlag4;
    }

    public void setTownRegionFlag4(String townRegionFlag4) {
        this.townRegionFlag4 = townRegionFlag4;
    }

    public String getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

}
