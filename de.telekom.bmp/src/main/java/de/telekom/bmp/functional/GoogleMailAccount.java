package de.telekom.bmp.functional;

import com.google.inject.Inject;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.MailAccount;
import de.telekom.googlemail.pages.Login;
import de.telekom.googlemail.pages.OldMail;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.controls.Link;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Mathias Herkt
 */
@Deprecated
public class GoogleMailAccount {

    private static final String GOOGLE_MAIL_URL = "mail.google.com";
    private static final String MAIL_PASSWORD = "galerien3?";

    public static final String CONFIRM_MAIL = "Bitte bestätigen";
    public static final String PW_RESET = "Passwortzurücksetzung";

    @Inject
    Datapool db;

    @Inject
    Login googlePage;

    @Inject
    OldMail readMailPage;

    MailAccount mailAccount;

    public void setMailAccount(String login) {
        String extractedMailAddress = extractEmailFromAlias(login);
        mailAccount = db.mailAccounts().field("mailAddress")
                .equal(extractedMailAddress).get();
        if (mailAccount == null) {
            mailAccount = new MailAccount();
            mailAccount.provider = GOOGLE_MAIL_URL;
            mailAccount.mailAddress = extractedMailAddress;
            mailAccount.password = MAIL_PASSWORD;
            db.save(mailAccount);
        }
    }

    /**
     * This methods navigates to a google account and try to find a mail, based
     * on a given partial string as subject. In this mail should be a link with
     * the given domain, which will be extracted.
     *
     * @param linkDomain is part of the BMP App domain in the link (e.g.
     * bmptest.de)
     * @param partialSubject text contains in subject
     * @return
     */
    public String checkGoogleMailAccountAndExtractConfirmLink(
            String linkDomain, String partialSubject) {
        navigateTo(mailAccount.provider);

        loginIntoGoogleMailAccount();

        String confirmLink = readMailAndFindConfirmLink(linkDomain,
                partialSubject);

        logoutMailAccount();

        return confirmLink;
    }

    private void loginIntoGoogleMailAccount() {
        set(googlePage.email, mailAccount.mailAddress);
        set(googlePage.password, mailAccount.password);
        if (googlePage.stayLoggedIn.isSelected()) {
            click(googlePage.stayLoggedIn);
        }

        click(googlePage.signIn);
    }

    private void logoutMailAccount() {
        click(googlePage.signoutLink);
    }

    private String extractEmailFromAlias(final String emailAddress) {
        String newMailAddress = emailAddress;
        if (emailAddress.contains("+")) {
            String mailName = emailAddress.substring(0,
                    emailAddress.indexOf("+"));
            String domainName = emailAddress.substring(emailAddress
                    .indexOf("@"));
            newMailAddress = mailName + domainName;
        }

        return newMailAddress;
    }

    private String readMailAndFindConfirmLink(String linkDomain, String mailCategory) {
        Link confirmRegLnk = readMailPage.emailLnk.get(mailCategory);
        assertThat(confirmRegLnk, is(not(nullValue())));
        click(confirmRegLnk);

        String reallyConfirm = readMailPage.confirmLink.get(linkDomain).getHref();
        assertThat(reallyConfirm, is(not(nullValue())));

        return reallyConfirm;
    }
}
