import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class css {
    
    static class GameProfile {
        String username;
        String uuid;
        
        GameProfile(String username, String uuid) {
            this.username = username;
            this.uuid = uuid;
        }
    }
    
    static class GameVersion {
        String id;
        String type;
        boolean installed;
        
        GameVersion(String id, String type, boolean installed) {
            this.id = id;
            this.type = type;
            this.installed = installed;
        }
    }
    
    static class MinecraftLauncher {
        List<GameProfile> profiles = new ArrayList<>();
        List<GameVersion> versions = new ArrayList<>();
        GameProfile selectedProfile;
        GameVersion selectedVersion;
        
        MinecraftLauncher() {
            initializeVersions();
            initializeProfiles();
        }
        
        void initializeVersions() {
            versions.add(new GameVersion("1.20.4", "release", true));
            versions.add(new GameVersion("1.20.1", "release", true));
            versions.add(new GameVersion("1.19.2", "release", true));
            versions.add(new GameVersion("23w51b", "snapshot", false));
            versions.add(new GameVersion("1.8.9", "release", true));
        }
        
        void initializeProfiles() {
            profiles.add(new GameProfile("Player1", "550e8400-e29b-41d4-a716-446655440000"));
            profiles.add(new GameProfile("Player2", "6ba7b810-9dad-11d1-80b4-00c04fd430c8"));
            profiles.add(new GameProfile("Player3", "6ba7b811-9dad-11d1-80b4-00c04fd430c8"));
        }
        
        void displayLauncher() {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║     Minecraft Java 版本啟動器模擬器        ║");
            System.out.println("╚══════════════════════════════════════════╝\n");
            
            System.out.println("【已安裝版本】");
            for (int i = 0; i < versions.size(); i++) {
                GameVersion v = versions.get(i);
                String status = v.installed ? "✓" : "✗";
                System.out.println(String.format("%d. %s [%s] %s", i + 1, v.id, v.type, status));
            }
            
            System.out.println("\n【玩家檔案】");
            for (int i = 0; i < profiles.size(); i++) {
                System.out.println(String.format("%d. %s", i + 1, profiles.get(i).username));
            }
            
            System.out.println("\n【選項】");
            System.out.println("1. 選擇版本");
            System.out.println("2. 選擇玩家");
            System.out.println("3. 啟動遊戲");
            System.out.println("4. 離開");
        }
        
        void selectVersion(int index) {
            if (index > 0 && index <= versions.size()) {
                selectedVersion = versions.get(index - 1);
                System.out.println("✓ 已選擇版本: " + selectedVersion.id);
                if (!selectedVersion.installed) {
                    System.out.println("⚠ 此版本未安裝，需要下載...");
                }
            } else {
                System.out.println("✗ 無效的版本編號");
            }
        }
        
        void selectProfile(int index) {
            if (index > 0 && index <= profiles.size()) {
                selectedProfile = profiles.get(index - 1);
                System.out.println("✓ 已選擇玩家: " + selectedProfile.username);
            } else {
                System.out.println("✗ 無效的玩家編號");
            }
        }
        
        void launchGame() {
            if (selectedVersion == null) {
                System.out.println("✗ 請先選擇版本");
                return;
            }
            if (selectedProfile == null) {
                System.out.println("✗ 請先選擇玩家");
                return;
            }
            
            if (!selectedVersion.installed) {
                System.out.println("正在下載版本 " + selectedVersion.id + "...");
                downloadVersion();
            }
            
            System.out.println("\n" + "═".repeat(42));
            System.out.println("正在啟動遊戲...");
            System.out.println("═".repeat(42));
            
            simulateLaunch();
        }
        
        void downloadVersion() {
            System.out.println("[████████████████████████] 100%");
            selectedVersion.installed = true;
            System.out.println("✓ 下載完成");
        }
        
        void simulateLaunch() {
            try {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                System.out.println("[" + timestamp + "] 正在準備環境...");
                Thread.sleep(800);
                
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] 讀取資源...");
                Thread.sleep(600);
                
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] 驗證玩家: " + selectedProfile.username);
                Thread.sleep(500);
                
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] UUID: " + selectedProfile.uuid);
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] 版本: " + selectedVersion.id);
                Thread.sleep(800);
                
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ✓ 啟動成功!");
                System.out.println("\n🎮 遊戲已啟動 - Minecraft " + selectedVersion.id);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) {
        MinecraftLauncher launcher = new MinecraftLauncher();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            launcher.displayLauncher();
            System.out.print("\n請選擇: ");
            
            try {
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.print("選擇版本 (1-" + launcher.versions.size() + "): ");
                        launcher.selectVersion(scanner.nextInt());
                        break;
                    case 2:
                        System.out.print("選擇玩家 (1-" + launcher.profiles.size() + "): ");
                        launcher.selectProfile(scanner.nextInt());
                        break;
                    case 3:
                        launcher.launchGame();
                        break;
                    case 4:
                        System.out.println("感謝遊玩！👋");
                        scanner.close();
                        return;
                    default:
                        System.out.println("✗ 無效的選項");
                }
            } catch (InputMismatchException e) {
                System.out.println("✗ 輸入有誤，請輸入數字");
                scanner.nextLine();
            }
        }
    }
}
