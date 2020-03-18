package cn.edu.hqu.cst.kubang.exhibition.controller;

import cn.edu.hqu.cst.kubang.exhibition.entity.Company;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
@Api(tags = "公司认证等服务")
public class CompanyController {

    @ApiOperation(value = "公司认证",notes = "接口待修改,不需要的参数：id,area,identifyStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "公司对象", required = true, dataType = "Company", paramType = "body")
    })
    @PostMapping("/identify")
    public void CompanyIdentify(@RequestBody Company company){
        String companyName =company.getName();
        String companyType =company.getType();
        String companyAddress =company.getAddress();
        String companyWebsite =company.getWebsite();
        String companyTelephone =company.getTelephone();
        String companyIntroduce=company.getIntroduction();

        String codeCertificateType = company.getCodeCertificateType();
        String codeCertificateId = company.getCodeCertificateId();
        String codeCertificatePic = company.getCodeCertificatePic();

        String responsiblePersonName = company.getResponsiblePersonName();
        String responsiblePersonIdCard = company.getResponsiblePersonIdcard();

        String headPicture = company.getHeadPicture();
  }
}
