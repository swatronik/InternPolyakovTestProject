import com.polyakov.service.bufferreader.DownloaderCodeWebsite;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

public class Tests {

    @Test
    public void urlInvalidTest() {
        String invalidUrl = "invalidUrl";

        try {
            DownloaderCodeWebsite downloaderCodeWebsite = new DownloaderCodeWebsite(invalidUrl);
            downloaderCodeWebsite.getReader();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MalformedURLException);
        }
    }
}
