package cn.edu.hqu.cst.kubang.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @RequestMapping("/identify")
    public void CompanyIdentify(HttpServletRequest request){
        String companyName =request.getParameter("companyName");
        String companyType =request.getParameter("type");
        String companyAddress =request.getParameter("address");
        String companyWebsite =request.getParameter("webSite");
        String companyTelephone =request.getParameter("telephone");
        String companyIntroduce=request.getParameter("introduce");

        String codeCertificateType = request.getParameter("codeCertificateType");
        String codeCertificateId = request.getParameter("codeCertificateId");
        String codeCertificatePic = request.getParameter("codeCertificatePic");

        String responsiblePersonName = request.getParameter("responsiblePersonName");
        String responsiblePersonIdCard = request.getParameter("responsiblePersonIdCard");

        String headPicture = request.getParameter("headPic");
  }
}
