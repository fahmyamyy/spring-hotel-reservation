package com.amyganz.notificationservice.services;

import com.amyganz.notificationservice.dtos.EmailRequest;
import com.amyganz.notificationservice.dtos.OTPEmail;
import com.amyganz.notificationservice.dtos.ReservationDetails;
import com.amyganz.notificationservice.helpers.AppHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class EmailService {
    private ObjectMapper objectMapper;
    private JavaMailSender mailSender;

    public void generateFile(ReservationDetails reservationDetails) throws IOException {
        PDDocument document = new PDDocument();

        // Create a new page
        PDPage page = new PDPage();
        document.addPage(page);

        // Define a font to use
        PDPageContentStream cs = new PDPageContentStream(document, document.getPage(0));

        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 20);
        cs.newLineAtOffset(270, 690);
        cs.showText("Hotellica");
        cs.endText();

        //Writing Multiple Lines
        //writing the customer details
//        cs.beginText();
//        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
//        cs.setLeading(20f);
//        cs.newLineAtOffset(60, 610);
//        cs.showText("Customer Name: ");
//        cs.newLine();
//        cs.showText("Phone Number: ");
//        cs.endText();

        cs.beginText();
        cs.setFont(PDType1Font.TIMES_ROMAN, 14);
        cs.setLeading(20f);
        cs.newLineAtOffset(170, 610);
        cs.showText("Reservator                      : " +reservationDetails.getName());
        cs.newLine();
        cs.showText("Hotel Name                      : " +reservationDetails.getHotel_name());
        cs.newLine();
        cs.showText("Room Type                      : " +reservationDetails.getRoom_type());
        cs.newLine();
        cs.showText("Room Number                  : " +reservationDetails.getRoom_number());
        cs.newLine();
        cs.showText("Check-in Date                  : " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()));
        cs.newLine();
        cs.showText("Check-out Date                : " +AppHelper.convertSDF(reservationDetails.getCheck_out_date()));
        cs.newLine();
        cs.showText("Total Payment                  : Rp. " +reservationDetails.getTotal_payment());
        cs.newLine();
        cs.showText("Status                                : PAID");
        cs.newLine();

        //Close the content stream
        cs.close();
        //Save the PDF
        document.save("invoice.pdf");


    }

//    @KafkaListener(groupId = "email-doc", topics = "mini-project")
    public void sendOtp(OTPEmail otpEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo("fahmyyamyy@gmail.com");
        helper.setTo(otpEmail.getEmail());
        helper.setReplyTo("customerservice@hotellica.com");
        helper.setSubject("Hotellica: OTP");
        helper.setText("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:500px;overflow:auto;line-height:2\">\n" +
                "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                "    <div style=\"border-bottom:1px solid #eee\">\n" +
                "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Hotellica</a>\n" +
                "    </div>\n" +
                "    <p style=\"font-size:1.1em\">Hi "+ otpEmail.getUsername() + ",</p>\n" +
                "    <p>Thank you for choosing Hotellica. Use the following OTP to Log in to your account. OTP is valid for 10 minutes</p>\n" +
                "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + otpEmail.getOtp() + "</h2>\n" +
                "    <p style=\"font-size:0.9em;\">Regards,<br />Hotellica</p>\n" +
                "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                "      <p>Hotellica Inc</p>\n" +
                "      <p>Indonesia, Jakarta/p>\n" +
                "      <p>California</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>", true);
//        helper.setText("Hi <b>" + otpEmail.getUsername() + ".</b><br> Your One-time Password (OTP) is:\n" +
//                "<h2>" + otpEmail.getOtp() + "</h2>\n" +
//                "\n This OTP valid for 10 Minutes.\n" +
//                "If you are having any issues with your account, please don't hesitate to contact us by replying to this email.",true);
        mailSender.send(message);
    }

//    @KafkaListener(groupId = "email-doc", topics = "mini-project")
    public void cancelEmail(ReservationDetails reservationDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo("fahmyyamyy@gmail.com");
        helper.setTo(reservationDetails.getEmail());
        helper.setReplyTo("customerservice@hotellica.com");
        helper.setSubject("Hotellica: Reservation Canceled");
        helper.setText("<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      /* -------------------------------------\n" +
                "          GLOBAL RESETS\n" +
                "      ------------------------------------- */\n" +
                "      \n" +
                "      /*All the styling goes here*/\n" +
                "      \n" +
                "      img {\n" +
                "        border: none;\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "        max-width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        font-family: sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%; \n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-collapse: separate;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        width: 100%; }\n" +
                "        table td {\n" +
                "          font-family: sans-serif;\n" +
                "          font-size: 14px;\n" +
                "          vertical-align: top; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BODY & CONTAINER\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "      .body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                "      .container {\n" +
                "        display: block;\n" +
                "        margin: 0 auto !important;\n" +
                "        /* makes it centered */\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px;\n" +
                "        width: 580px; \n" +
                "      }\n" +
                "\n" +
                "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                "      .content {\n" +
                "        box-sizing: border-box;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          HEADER, FOOTER, MAIN\n" +
                "      ------------------------------------- */\n" +
                "      .main {\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 3px;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      .wrapper {\n" +
                "        box-sizing: border-box;\n" +
                "        padding: 20px; \n" +
                "      }\n" +
                "\n" +
                "      .content-block {\n" +
                "        padding-bottom: 10px;\n" +
                "        padding-top: 10px;\n" +
                "      }\n" +
                "\n" +
                "      .footer {\n" +
                "        clear: both;\n" +
                "        margin-top: 10px;\n" +
                "        text-align: center;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "        .footer td,\n" +
                "        .footer p,\n" +
                "        .footer span,\n" +
                "        .footer a {\n" +
                "          color: #999999;\n" +
                "          font-size: 12px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          TYPOGRAPHY\n" +
                "      ------------------------------------- */\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4 {\n" +
                "        color: #000000;\n" +
                "        font-family: sans-serif;\n" +
                "        font-weight: 400;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 30px; \n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size: 35px;\n" +
                "        font-weight: 300;\n" +
                "        text-align: center;\n" +
                "        text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol {\n" +
                "        font-family: sans-serif;\n" +
                "        font-size: 14px;\n" +
                "        font-weight: normal;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 15px; \n" +
                "      }\n" +
                "        p li,\n" +
                "        ul li,\n" +
                "        ol li {\n" +
                "          list-style-position: inside;\n" +
                "          margin-left: 5px; \n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        color: #3498db;\n" +
                "        text-decoration: underline; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BUTTONS\n" +
                "      ------------------------------------- */\n" +
                "      .btn {\n" +
                "        box-sizing: border-box;\n" +
                "        width: 100%; }\n" +
                "        .btn > tbody > tr > td {\n" +
                "          padding-bottom: 15px; }\n" +
                "        .btn table {\n" +
                "          width: auto; \n" +
                "      }\n" +
                "        .btn table td {\n" +
                "          background-color: #ffffff;\n" +
                "          border-radius: 5px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "        .btn a {\n" +
                "          background-color: #ffffff;\n" +
                "          border: solid 1px #3498db;\n" +
                "          border-radius: 5px;\n" +
                "          box-sizing: border-box;\n" +
                "          color: #3498db;\n" +
                "          cursor: pointer;\n" +
                "          display: inline-block;\n" +
                "          font-size: 14px;\n" +
                "          font-weight: bold;\n" +
                "          margin: 0;\n" +
                "          padding: 12px 25px;\n" +
                "          text-decoration: none;\n" +
                "          text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary table td {\n" +
//                "        background-color: #3498db; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary a {\n" +
                "        background-color: #3498db;\n" +
                "        border-color: #3498db;\n" +
                "        color: #ffffff; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                "      ------------------------------------- */\n" +
                "      .last {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .first {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .align-center {\n" +
                "        text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      .align-right {\n" +
                "        text-align: right; \n" +
                "      }\n" +
                "\n" +
                "      .align-left {\n" +
                "        text-align: left; \n" +
                "      }\n" +
                "\n" +
                "      .clear {\n" +
                "        clear: both; \n" +
                "      }\n" +
                "\n" +
                "      .mt0 {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .mb0 {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .preheader {\n" +
                "        color: transparent;\n" +
                "        display: none;\n" +
                "        height: 0;\n" +
                "        max-height: 0;\n" +
                "        max-width: 0;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "        mso-hide: all;\n" +
                "        visibility: hidden;\n" +
                "        width: 0; \n" +
                "      }\n" +
                "\n" +
                "      .powered-by a {\n" +
                "        text-decoration: none; \n" +
                "      }\n" +
                "\n" +
                "      hr {\n" +
                "        border: 0;\n" +
                "        border-bottom: 1px solid #f6f6f6;\n" +
                "        margin: 20px 0; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "      ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table.body h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important; \n" +
                "        }\n" +
                "        table.body p,\n" +
                "        table.body ul,\n" +
                "        table.body ol,\n" +
                "        table.body td,\n" +
                "        table.body span,\n" +
                "        table.body a {\n" +
                "          font-size: 16px !important; \n" +
                "        }\n" +
                "        table.body .wrapper,\n" +
                "        table.body .article {\n" +
                "          padding: 10px !important; \n" +
                "        }\n" +
                "        table.body .content {\n" +
                "          padding: 0 !important; \n" +
                "        }\n" +
                "        table.body .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important; \n" +
                "        }\n" +
                "        table.body .btn table {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .btn a {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important; \n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          PRESERVE THESE STYLES IN THE HEAD\n" +
                "      ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%; \n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%; \n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important; \n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
//                "        .btn-primary table td:hover {\n" +
//                "          background-color: #34495e !important; \n" +
//                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important; \n" +
                "        } \n" +
                "      }\n" +
                "\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <span class=\"preheader\">Reservation Details</span>\n" +
                "    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "      <tr>\n" +
                "        <td>&nbsp;</td>\n" +
                "        <td class=\"container\">\n" +
                "          <div class=\"content\">\n" +
                "\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <table role=\"presentation\" class=\"main\">\n" +
                "\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td class=\"wrapper\">\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <p>Hi " + reservationDetails.getName() + ",</p>\n" +
                "                        <p>We're sorry to hear your reservation cancelation, here's the details :</p>\n" +

                "                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td align=\"left\">\n" +
                "                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td>Hotel Name</td>\n" +
                "                                      <td> : " + reservationDetails.getHotel_name() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Type</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_type() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Number</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_number() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-in Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-out Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Total Payment</td>\n" +
                "                                      <td> : " + reservationDetails.getTotal_payment() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Status</td>\n" +
                "                                      <td> :<b> " + reservationDetails.getStatus() + "</b></td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <p>Thank you for using Hotellica!</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div class=\"footer\">\n" +
                "              <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                  <td class=\"content-block\">\n" +
                "                    <span class=\"apple-link\">Indonesia, Jakarta</span>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
//                "                <tr>\n" +
//                "                  <td class=\"content-block powered-by\">\n" +
//                "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
//                "                  </td>\n" +
//                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td>&nbsp;</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>", true);
//
//        helper.setText("<h2>Your Reservation has been canceled</h2><br>\n" +
//                "<h4>Reservation details :</h4><br>\n" +
//                "Hotel name&emsp;&emsp;: " + reservationDetails.getHotel_name() + "<br>" +
//                "Room Type&emsp;&emsp;: " + reservationDetails.getRoom_type() + "<br>" +
//                "Room Number&emsp;&emsp;: " + reservationDetails.getRoom_number() + "<br>" +
//                "Check in Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "<br>" +
//                "Check out Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "<br>" +
//                "Total Payment&emsp;&emsp;: " + reservationDetails.getTotal_payment() + "<br>" +
//                "Status&emsp;&emsp;: " + "<b>" + reservationDetails.getStatus() + "</b>",true);
        mailSender.send(message);
    }

    public void newEmail(ReservationDetails reservationDetails) throws MessagingException, IOException {
//        generateFile(reservationDetails);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("fahmyyamyy@gmail.com");
        helper.setReplyTo("customerservice@hotellica.com");
        helper.setSubject("Hotellica: New Reservation");
        helper.setText("<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      /* -------------------------------------\n" +
                "          GLOBAL RESETS\n" +
                "      ------------------------------------- */\n" +
                "      \n" +
                "      /*All the styling goes here*/\n" +
                "      \n" +
                "      img {\n" +
                "        border: none;\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "        max-width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        font-family: sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%; \n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-collapse: separate;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        width: 100%; }\n" +
                "        table td {\n" +
                "          font-family: sans-serif;\n" +
                "          font-size: 14px;\n" +
                "          vertical-align: top; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BODY & CONTAINER\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "      .body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                "      .container {\n" +
                "        display: block;\n" +
                "        margin: 0 auto !important;\n" +
                "        /* makes it centered */\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px;\n" +
                "        width: 580px; \n" +
                "      }\n" +
                "\n" +
                "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                "      .content {\n" +
                "        box-sizing: border-box;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          HEADER, FOOTER, MAIN\n" +
                "      ------------------------------------- */\n" +
                "      .main {\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 3px;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      .wrapper {\n" +
                "        box-sizing: border-box;\n" +
                "        padding: 20px; \n" +
                "      }\n" +
                "\n" +
                "      .content-block {\n" +
                "        padding-bottom: 10px;\n" +
                "        padding-top: 10px;\n" +
                "      }\n" +
                "\n" +
                "      .footer {\n" +
                "        clear: both;\n" +
                "        margin-top: 10px;\n" +
                "        text-align: center;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "        .footer td,\n" +
                "        .footer p,\n" +
                "        .footer span,\n" +
                "        .footer a {\n" +
                "          color: #999999;\n" +
                "          font-size: 12px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          TYPOGRAPHY\n" +
                "      ------------------------------------- */\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4 {\n" +
                "        color: #000000;\n" +
                "        font-family: sans-serif;\n" +
                "        font-weight: 400;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 30px; \n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size: 35px;\n" +
                "        font-weight: 300;\n" +
                "        text-align: center;\n" +
                "        text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol {\n" +
                "        font-family: sans-serif;\n" +
                "        font-size: 14px;\n" +
                "        font-weight: normal;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 15px; \n" +
                "      }\n" +
                "        p li,\n" +
                "        ul li,\n" +
                "        ol li {\n" +
                "          list-style-position: inside;\n" +
                "          margin-left: 5px; \n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        color: #3498db;\n" +
                "        text-decoration: underline; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BUTTONS\n" +
                "      ------------------------------------- */\n" +
                "      .btn {\n" +
                "        box-sizing: border-box;\n" +
                "        width: 100%; }\n" +
                "        .btn > tbody > tr > td {\n" +
                "          padding-bottom: 15px; }\n" +
                "        .btn table {\n" +
                "          width: auto; \n" +
                "      }\n" +
                "        .btn table td {\n" +
                "          background-color: #ffffff;\n" +
                "          border-radius: 5px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "        .btn a {\n" +
                "          background-color: #ffffff;\n" +
                "          border: solid 1px #3498db;\n" +
                "          border-radius: 5px;\n" +
                "          box-sizing: border-box;\n" +
                "          color: #3498db;\n" +
                "          cursor: pointer;\n" +
                "          display: inline-block;\n" +
                "          font-size: 14px;\n" +
                "          font-weight: bold;\n" +
                "          margin: 0;\n" +
                "          padding: 12px 25px;\n" +
                "          text-decoration: none;\n" +
                "          text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary table td {\n" +
//                "        background-color: #3498db; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary a {\n" +
                "        background-color: #3498db;\n" +
                "        border-color: #3498db;\n" +
                "        color: #ffffff; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                "      ------------------------------------- */\n" +
                "      .last {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .first {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .align-center {\n" +
                "        text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      .align-right {\n" +
                "        text-align: right; \n" +
                "      }\n" +
                "\n" +
                "      .align-left {\n" +
                "        text-align: left; \n" +
                "      }\n" +
                "\n" +
                "      .clear {\n" +
                "        clear: both; \n" +
                "      }\n" +
                "\n" +
                "      .mt0 {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .mb0 {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .preheader {\n" +
                "        color: transparent;\n" +
                "        display: none;\n" +
                "        height: 0;\n" +
                "        max-height: 0;\n" +
                "        max-width: 0;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "        mso-hide: all;\n" +
                "        visibility: hidden;\n" +
                "        width: 0; \n" +
                "      }\n" +
                "\n" +
                "      .powered-by a {\n" +
                "        text-decoration: none; \n" +
                "      }\n" +
                "\n" +
                "      hr {\n" +
                "        border: 0;\n" +
                "        border-bottom: 1px solid #f6f6f6;\n" +
                "        margin: 20px 0; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "      ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table.body h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important; \n" +
                "        }\n" +
                "        table.body p,\n" +
                "        table.body ul,\n" +
                "        table.body ol,\n" +
                "        table.body td,\n" +
                "        table.body span,\n" +
                "        table.body a {\n" +
                "          font-size: 16px !important; \n" +
                "        }\n" +
                "        table.body .wrapper,\n" +
                "        table.body .article {\n" +
                "          padding: 10px !important; \n" +
                "        }\n" +
                "        table.body .content {\n" +
                "          padding: 0 !important; \n" +
                "        }\n" +
                "        table.body .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important; \n" +
                "        }\n" +
                "        table.body .btn table {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .btn a {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important; \n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          PRESERVE THESE STYLES IN THE HEAD\n" +
                "      ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%; \n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%; \n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important; \n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
//                "        .btn-primary table td:hover {\n" +
//                "          background-color: #34495e !important; \n" +
//                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important; \n" +
                "        } \n" +
                "      }\n" +
                "\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <span class=\"preheader\">Reservation Details</span>\n" +
                "    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "      <tr>\n" +
                "        <td>&nbsp;</td>\n" +
                "        <td class=\"container\">\n" +
                "          <div class=\"content\">\n" +
                "\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <table role=\"presentation\" class=\"main\">\n" +
                "\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td class=\"wrapper\">\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <p>Hi " + reservationDetails.getName() + ",</p>\n" +
                "                        <p>We received your reservation, here's the details :</p>\n" +

                "                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td align=\"left\">\n" +
                "                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td>Hotel Name</td>\n" +
                "                                      <td> : " + reservationDetails.getHotel_name() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Type</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_type() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Number</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_number() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-in Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-out Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Total Payment</td>\n" +
                "                                      <td> : " + reservationDetails.getTotal_payment() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Status</td>\n" +
                "                                      <td> :<b> " + reservationDetails.getStatus() + "</b></td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <p>Thank you for using Hotellica!</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div class=\"footer\">\n" +
                "              <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                  <td class=\"content-block\">\n" +
                "                    <span class=\"apple-link\">Indonesia, Jakarta</span>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
//                "                <tr>\n" +
//                "                  <td class=\"content-block powered-by\">\n" +
//                "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
//                "                  </td>\n" +
//                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td>&nbsp;</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>", true);
//        helper.setText("<h2>Here's your reservation</h2><br>\n" +
//                "<h4>Reservation details :</h4><br>\n" +
//                "Hotel name&emsp;&emsp;: " + reservationDetails.getHotel_name() + "<br>" +
//                "Room Type&emsp;&emsp;: " + reservationDetails.getRoom_type() + "<br>" +
//                "Room Number&emsp;&emsp;: " + reservationDetails.getRoom_number() + "<br>" +
//                "Check in Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "<br>" +
//                "Check out Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "<br>" +
//                "Total Payment&emsp;&emsp;: " + reservationDetails.getTotal_payment() + "<br>" +
//                "Status&emsp;&emsp;: " + "<b>" + reservationDetails.getStatus() + "</b>",true);
        mailSender.send(message);
    }

    public void paidEmail(ReservationDetails reservationDetails) throws MessagingException, IOException {
        generateFile(reservationDetails);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo("fahmyyamyy@gmail.com");
        helper.setTo(reservationDetails.getEmail());
        helper.setReplyTo("customerservice@hotellica.com");
        helper.setSubject("Hotellica: Reservation Paid");
        helper.setText("<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      /* -------------------------------------\n" +
                "          GLOBAL RESETS\n" +
                "      ------------------------------------- */\n" +
                "      \n" +
                "      /*All the styling goes here*/\n" +
                "      \n" +
                "      img {\n" +
                "        border: none;\n" +
                "        -ms-interpolation-mode: bicubic;\n" +
                "        max-width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        font-family: sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        -ms-text-size-adjust: 100%;\n" +
                "        -webkit-text-size-adjust: 100%; \n" +
                "      }\n" +
                "\n" +
                "      table {\n" +
                "        border-collapse: separate;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        width: 100%; }\n" +
                "        table td {\n" +
                "          font-family: sans-serif;\n" +
                "          font-size: 14px;\n" +
                "          vertical-align: top; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BODY & CONTAINER\n" +
                "      ------------------------------------- */\n" +
                "\n" +
                "      .body {\n" +
                "        background-color: #f6f6f6;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                "      .container {\n" +
                "        display: block;\n" +
                "        margin: 0 auto !important;\n" +
                "        /* makes it centered */\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px;\n" +
                "        width: 580px; \n" +
                "      }\n" +
                "\n" +
                "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                "      .content {\n" +
                "        box-sizing: border-box;\n" +
                "        display: block;\n" +
                "        margin: 0 auto;\n" +
                "        max-width: 580px;\n" +
                "        padding: 10px; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          HEADER, FOOTER, MAIN\n" +
                "      ------------------------------------- */\n" +
                "      .main {\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 3px;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "\n" +
                "      .wrapper {\n" +
                "        box-sizing: border-box;\n" +
                "        padding: 20px; \n" +
                "      }\n" +
                "\n" +
                "      .content-block {\n" +
                "        padding-bottom: 10px;\n" +
                "        padding-top: 10px;\n" +
                "      }\n" +
                "\n" +
                "      .footer {\n" +
                "        clear: both;\n" +
                "        margin-top: 10px;\n" +
                "        text-align: center;\n" +
                "        width: 100%; \n" +
                "      }\n" +
                "        .footer td,\n" +
                "        .footer p,\n" +
                "        .footer span,\n" +
                "        .footer a {\n" +
                "          color: #999999;\n" +
                "          font-size: 12px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          TYPOGRAPHY\n" +
                "      ------------------------------------- */\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4 {\n" +
                "        color: #000000;\n" +
                "        font-family: sans-serif;\n" +
                "        font-weight: 400;\n" +
                "        line-height: 1.4;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 30px; \n" +
                "      }\n" +
                "\n" +
                "      h1 {\n" +
                "        font-size: 35px;\n" +
                "        font-weight: 300;\n" +
                "        text-align: center;\n" +
                "        text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      p,\n" +
                "      ul,\n" +
                "      ol {\n" +
                "        font-family: sans-serif;\n" +
                "        font-size: 14px;\n" +
                "        font-weight: normal;\n" +
                "        margin: 0;\n" +
                "        margin-bottom: 15px; \n" +
                "      }\n" +
                "        p li,\n" +
                "        ul li,\n" +
                "        ol li {\n" +
                "          list-style-position: inside;\n" +
                "          margin-left: 5px; \n" +
                "      }\n" +
                "\n" +
                "      a {\n" +
                "        color: #3498db;\n" +
                "        text-decoration: underline; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          BUTTONS\n" +
                "      ------------------------------------- */\n" +
                "      .btn {\n" +
                "        box-sizing: border-box;\n" +
                "        width: 100%; }\n" +
                "        .btn > tbody > tr > td {\n" +
                "          padding-bottom: 15px; }\n" +
                "        .btn table {\n" +
                "          width: auto; \n" +
                "      }\n" +
                "        .btn table td {\n" +
                "          background-color: #ffffff;\n" +
                "          border-radius: 5px;\n" +
                "          text-align: center; \n" +
                "      }\n" +
                "        .btn a {\n" +
                "          background-color: #ffffff;\n" +
                "          border: solid 1px #3498db;\n" +
                "          border-radius: 5px;\n" +
                "          box-sizing: border-box;\n" +
                "          color: #3498db;\n" +
                "          cursor: pointer;\n" +
                "          display: inline-block;\n" +
                "          font-size: 14px;\n" +
                "          font-weight: bold;\n" +
                "          margin: 0;\n" +
                "          padding: 12px 25px;\n" +
                "          text-decoration: none;\n" +
                "          text-transform: capitalize; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary table td {\n" +
//                "        background-color: #3498db; \n" +
                "      }\n" +
                "\n" +
                "      .btn-primary a {\n" +
                "        background-color: #3498db;\n" +
                "        border-color: #3498db;\n" +
                "        color: #ffffff; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                "      ------------------------------------- */\n" +
                "      .last {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .first {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .align-center {\n" +
                "        text-align: center; \n" +
                "      }\n" +
                "\n" +
                "      .align-right {\n" +
                "        text-align: right; \n" +
                "      }\n" +
                "\n" +
                "      .align-left {\n" +
                "        text-align: left; \n" +
                "      }\n" +
                "\n" +
                "      .clear {\n" +
                "        clear: both; \n" +
                "      }\n" +
                "\n" +
                "      .mt0 {\n" +
                "        margin-top: 0; \n" +
                "      }\n" +
                "\n" +
                "      .mb0 {\n" +
                "        margin-bottom: 0; \n" +
                "      }\n" +
                "\n" +
                "      .preheader {\n" +
                "        color: transparent;\n" +
                "        display: none;\n" +
                "        height: 0;\n" +
                "        max-height: 0;\n" +
                "        max-width: 0;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "        mso-hide: all;\n" +
                "        visibility: hidden;\n" +
                "        width: 0; \n" +
                "      }\n" +
                "\n" +
                "      .powered-by a {\n" +
                "        text-decoration: none; \n" +
                "      }\n" +
                "\n" +
                "      hr {\n" +
                "        border: 0;\n" +
                "        border-bottom: 1px solid #f6f6f6;\n" +
                "        margin: 20px 0; \n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                "      ------------------------------------- */\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table.body h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important; \n" +
                "        }\n" +
                "        table.body p,\n" +
                "        table.body ul,\n" +
                "        table.body ol,\n" +
                "        table.body td,\n" +
                "        table.body span,\n" +
                "        table.body a {\n" +
                "          font-size: 16px !important; \n" +
                "        }\n" +
                "        table.body .wrapper,\n" +
                "        table.body .article {\n" +
                "          padding: 10px !important; \n" +
                "        }\n" +
                "        table.body .content {\n" +
                "          padding: 0 !important; \n" +
                "        }\n" +
                "        table.body .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important; \n" +
                "        }\n" +
                "        table.body .btn table {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .btn a {\n" +
                "          width: 100% !important; \n" +
                "        }\n" +
                "        table.body .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important; \n" +
                "        }\n" +
                "      }\n" +
                "\n" +
                "      /* -------------------------------------\n" +
                "          PRESERVE THESE STYLES IN THE HEAD\n" +
                "      ------------------------------------- */\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%; \n" +
                "        }\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%; \n" +
                "        }\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important; \n" +
                "        }\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
//                "        .btn-primary table td:hover {\n" +
//                "          background-color: #34495e !important; \n" +
//                "        }\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important; \n" +
                "        } \n" +
                "      }\n" +
                "\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <span class=\"preheader\">Reservation Details</span>\n" +
                "    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                "      <tr>\n" +
                "        <td>&nbsp;</td>\n" +
                "        <td class=\"container\">\n" +
                "          <div class=\"content\">\n" +
                "\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <table role=\"presentation\" class=\"main\">\n" +
                "\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td class=\"wrapper\">\n" +
                "                  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                    <tr>\n" +
                "                      <td>\n" +
                "                        <p>Hi " + reservationDetails.getName() + ",</p>\n" +
                "                        <p>We received your payment, here's your reservation details :</p>\n" +

                "                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td align=\"left\">\n" +
                "                                <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td>Hotel Name</td>\n" +
                "                                      <td> : " + reservationDetails.getHotel_name() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Type</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_type() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Room Number</td>\n" +
                "                                      <td> : " + reservationDetails.getRoom_number() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-in Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Check-out Date</td>\n" +
                "                                      <td> : " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Total Payment</td>\n" +
                "                                      <td> : " + reservationDetails.getTotal_payment() + "</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                      <td>Status</td>\n" +
                "                                      <td> :<b> " + reservationDetails.getStatus() + "</b></td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <p>Thank you for using Hotellica!</p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div class=\"footer\">\n" +
                "              <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                <tr>\n" +
                "                  <td class=\"content-block\">\n" +
                "                    <span class=\"apple-link\">Indonesia, Jakarta</span>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
//                "                <tr>\n" +
//                "                  <td class=\"content-block powered-by\">\n" +
//                "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
//                "                  </td>\n" +
//                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td>&nbsp;</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>", true);
//        helper.setText("<h2>Payment Received</h2><br>\n" +
//                "<h4>Reservation details :</h4><br>\n" +
//                "Hotel name&emsp;&emsp;: " + reservationDetails.getHotel_name() + "<br>" +
//                "Room Type&emsp;&emsp;: " + reservationDetails.getRoom_type() + "<br>" +
//                "Room Number&emsp;&emsp;: " + reservationDetails.getRoom_number() + "<br>" +
//                "Check in Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_in_date()) + "<br>" +
//                "Check out Date&emsp;&emsp;: " + AppHelper.convertSDF(reservationDetails.getCheck_out_date()) + "<br>" +
//                "Total Payment&emsp;&emsp;: " + reservationDetails.getTotal_payment() + "<br>" +
//                "Payment Method&emsp;&emsp;: " + reservationDetails.getPayment() + "<br>" +
//                "Status&emsp;&emsp;: " + "<b>" + reservationDetails.getStatus() + "</b>",true);
        helper.addAttachment("invoice.pdf", new File(Path.of("invoice.pdf").toUri()));
        mailSender.send(message);
        Files.deleteIfExists(Path.of("invoice.pdf"));
    }
//    @KafkaListener(groupId = "email-doc", topics = "mini-project")
//    public void sendEmail(String to, String subject, String body) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(body);
//        mailSender.send(message);
//    }

    @KafkaListener(groupId = "email-doc", topics = "mini-project")
    public void sendEmailWithDocument(String user) throws JsonProcessingException {
//        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
//        ObjectMapper objectMapper = new ObjectMapper();
        EmailRequest emailRequest = objectMapper.readValue(user, EmailRequest.class);
        System.out.println(user);
    }

    @KafkaListener(groupId = "email-doc", topics = "otp")
    public void otp(String user) throws JsonProcessingException, MessagingException {
//        EmailRequest emailRequest = objectMapper.readValue(user, EmailRequest.class);
        OTPEmail otpEmail = objectMapper.readValue(user, OTPEmail.class);
        sendOtp(otpEmail);
//        System.out.println(user);
    }

    @KafkaListener(groupId = "email-doc", topics = "new-reservation")
    public void newReservation(String reservation) throws IOException, MessagingException {
//        System.out.println(reservation);
        ReservationDetails reservationDetails = objectMapper.readValue(reservation, ReservationDetails.class);
        newEmail(reservationDetails);
    }

    @KafkaListener(groupId = "email-doc", topics = "cancel-reservation")
    public void cancelReservation(String reservation) throws JsonProcessingException, MessagingException {
        ReservationDetails reservationDetails = objectMapper.readValue(reservation, ReservationDetails.class);
        cancelEmail(reservationDetails);
    }

    @KafkaListener(groupId = "email-doc", topics = "paid-reservation")
    public void paidReservation(String reservation) throws IOException, MessagingException {
        ReservationDetails reservationDetails = objectMapper.readValue(reservation, ReservationDetails.class);
        paidEmail(reservationDetails);
    }
}
