import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MinecraftLauncher {
    public static void main(String[] args) {
        String url = "https://www.minecraft.net/zh-hant/download";
        System.out.println("Minecraft 啟動器正在準備開啟官方下載頁面...");

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("已於預設瀏覽器開啟: " + url);
            } catch (IOException | URISyntaxException e) {
                System.err.println("無法開啟瀏覽器: " + e.getMessage());
                System.out.println("請手動打開以下網址：");
                System.out.println(url);
            }
        } else {
            System.out.println("此平台不支援 Desktop API。請手動打開以下網址：");
            System.out.println(url);
        }
    }
}
