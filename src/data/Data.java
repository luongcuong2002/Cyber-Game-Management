package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Computer;
import models.ComputerGroup;
import models.ServiceCategory;
import models.ServiceItem;
import models.TransactionHistoryItem;
import models.User;
import models.UserGroup;

public abstract class Data {

    private static final String rootPath = System.getProperty("user.dir") + File.separator + "data";
    private static final String computerGroupPath = rootPath + File.separator + "computer_group.json";
    private static final String computerPath = rootPath + File.separator + "computer.json";
    private static final String userPath = rootPath + File.separator + "user.json";
    private static final String servicePath = rootPath + File.separator + "service.json";
    private static final String transactionHistoryPath = rootPath + File.separator + "transaction_history.json";

    public static ArrayList<ComputerGroup> computerGroups = new ArrayList<>();
    public static ArrayList<Computer> listComputers = new ArrayList<>();
    public static ArrayList<User> listUsers = new ArrayList<>();
    public static ArrayList<ServiceCategory> listServiceCategories = new ArrayList<>();
    public static ArrayList<TransactionHistoryItem> listTransactionHistoryItems = new ArrayList<>();

    public static void getData() {
        if (!new File(rootPath).exists()) {
            try {
                throw new Exception("Data folder not found!");
            } catch (Exception ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        setUpComputerGroups();
        setUpListComputers();
        setUpListUsers();
        setUpListServiceCategories();
        setUpListTransactionHistoryItems();
    }

    public static void saveData() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        File file;
        FileWriter fw;
        if (!new File(rootPath).exists()) {
            new File(rootPath).mkdir();
        }

        try {
            file = new File(computerGroupPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            String computerGroupJson = gson.toJson(computerGroups);
            fw.write(computerGroupJson);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            file = new File(computerPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            String computerJson = gson.toJson(listComputers);
            fw.write(computerJson);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            file = new File(userPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            String userJson = gson.toJson(listUsers);
            fw.write(userJson);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            file = new File(servicePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            String serviceJson = gson.toJson(listServiceCategories);
            fw.write(serviceJson);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            file = new File(transactionHistoryPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            String transactionHistoryJson = gson.toJson(listTransactionHistoryItems);
            fw.write(transactionHistoryJson);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void setUpComputerGroups() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            File file = new File(computerGroupPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<ArrayList<ComputerGroup>>() {
            }.getType();
            computerGroups = gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (computerGroups == null) {
            try {
                throw new Exception("No computer group has found!");
//                ArrayList<UserGroup> userGroups2 = new ArrayList<>();
//                userGroups2.add(new UserGroup("Guest", 5000));
//                userGroups2.add(new UserGroup("Member", 5000));
//                computerGroups.add(new ComputerGroup("Default", "Mặc định", userGroups2));
//
//                ArrayList<UserGroup> userGroups = new ArrayList<>();
//                userGroups.add(new UserGroup("Guest", 6000));
//                userGroups.add(new UserGroup("Member", 5500));
//                computerGroups.add(new ComputerGroup("NEW", "Máy mới", userGroups));
//
//                ArrayList<UserGroup> userGroups1 = new ArrayList<>();
//                userGroups1.add(new UserGroup("Guest", 4500));
//                userGroups1.add(new UserGroup("Member", 4000));
//                computerGroups.add(new ComputerGroup("OLD", "Máy cũ", userGroups1));
            } catch (Exception ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void setUpListComputers() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            File file = new File(computerPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<ArrayList<Computer>>() {
            }.getType();
            listComputers = gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (listComputers == null) {
            listComputers = new ArrayList<>();
            try {
                throw new Exception("No computer has found!");
//            listComputers.add(new Computer("MAY01", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY02", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY03", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY04", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY05", computerGroups.get(2)));
//            listComputers.add(new Computer("MAY06", computerGroups.get(2)));
//            listComputers.add(new Computer("MAY07", computerGroups.get(2)));
//            listComputers.add(new Computer("MAY08", computerGroups.get(2)));
//            listComputers.add(new Computer("MAY09", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY10", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY11", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY12", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY13", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY15", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY16", computerGroups.get(1)));
//            listComputers.add(new Computer("MAY17", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY18", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY19", computerGroups.get(0)));
//            listComputers.add(new Computer("MAY20", computerGroups.get(0)));
            } catch (Exception ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (int i = 0; i < listComputers.size(); i++) {
                listComputers.get(i).setListServicesOrdered(new ArrayList<>());
                listComputers.get(i).setListTransactionsTransfer(new ArrayList<>());
            }
        }
    }

    private static void setUpListUsers() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            File file = new File(userPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            listUsers = gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (listUsers == null) {
            try {
                throw new Exception("Can't read user.json!");
//                listUsers.add(new User("NLcuong", "1", "Member"));
//                listUsers.get(listUsers.size() - 1).setRemainingAmount(10000);
//                listUsers.add(new User("User1", "1", "Member"));
//                listUsers.get(listUsers.size() - 1).setRemainingAmount(15000);
//                listUsers.add(new User("User2", "1", "Member"));
//                listUsers.add(new User("User3", "1", "Member"));
//                listUsers.add(new User("User4", "1", "Member"));
//                listUsers.add(new User("User5", "1", "Member"));
//                listUsers.add(new User("User6", "1", "Member"));
//                listUsers.add(new User("User7", "1", "Member"));
            } catch (Exception e) {
            }
        }
    }

    private static void setUpListServiceCategories() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            File file = new File(servicePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<ArrayList<ServiceCategory>>() {
            }.getType();
            listServiceCategories = gson.fromJson(json, listType);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (listServiceCategories == null) {
            try {
                throw new Exception("Can't read service.json");
//                listServiceCategories.add(new ServiceCategory("ĐỒ VẶT"));
//                listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM THƯỜNG", 5000, "VNĐ", "ĐỒ VẶT"));
//                listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM POCA", 7000, "VNĐ", "ĐỒ VẶT"));
//                listServiceCategories.get(0).addServiceItem(new ServiceItem("BIM MIX", 12000, "VNĐ", "ĐỒ VẶT"));
//                listServiceCategories.get(0).addServiceItem(new ServiceItem("ĐẬU PHỘNG", 15000, "VNĐ", "ĐỒ VẶT"));
//
//                listServiceCategories.add(new ServiceCategory("MÌ TÔM"));
//                listServiceCategories.get(1).addServiceItem(new ServiceItem("1 MÌ 1 TRỨNG 1 XÚC XÍCH", 30000, "VNĐ", "MÌ TÔM"));
//                listServiceCategories.get(1).addServiceItem(new ServiceItem("2 MÌ 1 TRỨNG 1 XÚC XÍCH", 35000, "VNĐ", "MÌ TÔM"));
//                listServiceCategories.get(1).addServiceItem(new ServiceItem("XÚC XÍCH RÁN", 10000, "VNĐ", "MÌ TÔM"));
//                listServiceCategories.get(1).addServiceItem(new ServiceItem("MÌ TRỨNG XÚC XÍCH RAU", 30000, "VNĐ", "MÌ TÔM"));
//
//                listServiceCategories.add(new ServiceCategory("NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("BÒ HÚC", 15000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC C2", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC DỪA", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING ĐỎ", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING ĐEN", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC STING VÀNG", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC LỌC", 5000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC TRÀ ĐÀO", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC YẾN", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC Ô LONG", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC COCA", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC PEPSI", 10000, "VNĐ", "NƯỚC"));
//                listServiceCategories.get(2).addServiceItem(new ServiceItem("NƯỚC 7 UP", 10000, "VNĐ", "NƯỚC"));
            } catch (Exception e) {
            }
        }
    }

    private static void setUpListTransactionHistoryItems() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try {
            File file = new File(transactionHistoryPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            Type listType = new TypeToken<ArrayList<TransactionHistoryItem>>() {
            }.getType();
            ArrayList<TransactionHistoryItem> list = gson.fromJson(json, listType);
            if (list != null) {
                listTransactionHistoryItems = list;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if(listTransactionHistoryItems == null){
            listTransactionHistoryItems = new ArrayList<>();
            System.out.println("A error occurred when read the file 'TransactionHistory.json'!");
        }
    }

    public static User getUserByUserName(String userName) {

        // bad structure: Mỗi một class chỉ nên thực hiện một chức năng nhất định
        for (int i = 0; i < Data.listUsers.size(); i++) {
            if (Data.listUsers.get(i).getUserName().equals(userName)) {
                return Data.listUsers.get(i);
            }
        }
        return null;
    }

    public static User getUserPrePaidByUserName(String userName) {

        // bad structure: Mỗi một class chỉ nên thực hiện một chức năng nhất định
        for (int i = 0; i < Data.listUsers.size(); i++) {
            User user = Data.listUsers.get(i);
            if (user.getUserGroupName().equals("Guest") && user.isIsPrepaid()) {
                return user;
            }
        }
        return null;
    }

    public static Computer getComputerByComputerName(String computerName) {

        // bad structure: Mỗi một class chỉ nên thực hiện một chức năng nhất định
        for (int i = 0; i < Data.listComputers.size(); i++) {
            if (Data.listComputers.get(i).getComputerName().equals(computerName)) {
                return Data.listComputers.get(i);
            }
        }
        return null;
    }

    public static ComputerGroup getComputerGroupByName(String groupName) {

        // bad structure: Mỗi một class chỉ nên thực hiện một chức năng nhất định
        for (int i = 0; i < Data.computerGroups.size(); i++) {
            if (Data.computerGroups.get(i).getGroupName().equals(groupName)) {
                return Data.computerGroups.get(i);
            }
        }
        return null;
    }
}
