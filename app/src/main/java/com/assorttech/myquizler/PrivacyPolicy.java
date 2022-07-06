package com.assorttech.myquizler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PrivacyPolicy extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        textView= findViewById(R.id.privacy_policy);

        textView.setText("We built the My Quizler app as a Free app. This Application is provided by Assort Tech (Pvt) Ltd. at no cost and is intended for use as is.\n" +
                "\n" +
                "This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use this Application.\n" +
                "\n" +
                "If you choose to use this Application, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Application. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                "\n" +
                "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions.\n" +
                "\n" +
                "Information Collection and Use\n" +
                "\n" +
                "For a better experience, while using our Application, We may require you to provide us with certain personally identifiable information (i.e: Device Name, Android Version). The information that we request will be retained on your device and is not collected by us in any way.\n" +
                "\n" +
                "We require and use phone number to authenticate and register users. This information will be retained with great security and not shared with anyone.\n" +
                "\n" +
                "The app does use third party services that may collect information used to identify you.\n" +
                "\n" +
                "Link to privacy policy of third party Application providers used by the app\n" +
                "\n" +
                "Google Play Services [https://policies.google.com/privacy]\n" +
                "Google Firebase [https://firebase.google.com/support/privacy/]\n" +
                "Log Data\n" +
                "\n" +
                "We want to inform you that whenever you use our Application, in a case of an error in the app we collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Application, the time and date of your use of the Application, and other statistics.\n" +
                "\n" +
                "Cookies\n" +
                "\n" +
                "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                "\n" +
                "This Application does not use these “cookies” explicitly. You have the option to either accept or refuse cookies and know when a cookie is being sent to your device. You can choose to refuse our cookies.\n" +
                "\n" +
                "Application Providers\n" +
                "\n" +
                "We may employ third-party companies and individuals in future due to the following reasons:\n" +
                "\n" +
                "To facilitate our Application.\n" +
                "To assist us in analysing how our Application is used.\n" +
                "We want to inform users of this Application that you'll be informed when these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose. This will be done only after you allow to share the requested information.\n" +
                "\n" +
                "Security\n" +
                "\n" +
                "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security. But since we try our best to take all security measures so your presence is secure.\n" +
                "Links to Other Sites\n" +
                "\n" +
                "This Application contain links to our sites. If you click on the link, you will be directed to that site. Note that these external sites are not operated by us. But we Trust them all.\n" +
                "\n" +
                "Changes to This Privacy Policy\n" +
                "\n" +
                "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page.\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us\n"+"\n");
    }
}
