import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class generate_bill extends JFrame implements ActionListener{
    JLabel l1;
    JTextArea t1;
    JButton b1;
    Choice c1,c2;
    JPanel p1;
    generate_bill(){
        setSize(500,900);
        setLayout(new BorderLayout());

        p1 = new JPanel();

        l1 = new JLabel("Generate Bill");
        conn c = new conn();

        List l_meter = new ArrayList();


        try{
            String a = "select * from emp";
            ResultSet rs = c.s.executeQuery(a);
            while (rs.next()){
                String con = rs.getString("MeterNumber");
                l_meter.add(con);
            }

        }catch (Exception e){
            System.out.println(e);
        }

        c1 = new Choice();
        c2 = new Choice();

        for (int y =0 ; y< l_meter.size(); y++){
            c1.add((String) l_meter.get(y));
        }

        c2.add("January");
        c2.add("February");
        c2.add("March");
        c2.add("April");
        c2.add("May");
        c2.add("June");
        c2.add("July");
        c2.add("August");
        c2.add("September");
        c2.add("October");
        c2.add("November");
        c2.add("December");


        t1 = new JTextArea(50,15);
        JScrollPane jsp = new JScrollPane(t1);
        t1.setFont(new Font("Senserif",Font.ITALIC,18));

        b1 = new JButton("Generate Bill");

        p1.add(l1);
        p1.add(c1);
        p1.add(c2);
        add(p1,"North");

        add(jsp,"Center");
        add(b1,"South");

        b1.addActionListener(this);

        setLocation(350,40);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            conn c = new conn();

            String month = c2.getSelectedItem();
            t1.setText("\tElectricity Billing System (GP-3)\nELECTRICITY BILL FOR THE MONTH OF "+month+" ,2018\n\n\n");

            ResultSet rs = c.s.executeQuery("select * from emp where MeterNumber="+c1.getSelectedItem());

            if(rs.next()){
                t1.append("\n    Customer Name:"+rs.getString("name"));
                t1.append("\n    Meter Number:  "+rs.getString("MeterNumber"));
                t1.append("\n    Address:            "+rs.getString("address"));
                t1.append("\n    State:                 "+rs.getString("state"));
                t1.append("\n    City:                   "+rs.getString("city"));
                t1.append("\n    Email:                "+rs.getString("email"));
                t1.append("\n    Phone Number  "+rs.getString("phone"));
                t1.append("\n-------------------------------------------------------------");
                t1.append("\n");
            }

            String query = "select * from bill where MeterNumber='"+c1.getSelectedItem()+"'and Month='"+month+"'";


            rs = c.s.executeQuery(query);

            if(rs.next()){
                t1.append("\n    Current Month :\t"+rs.getString("month"));
                t1.append("\n    Units Consumed:\t"+rs.getString("units"));
                t1.append("\n    Total Charges :\t"+rs.getString("amount"));
                t1.append("\n---------------------------------------------------------------");
                t1.append("\n    TOTAL PAYABLE :\t"+rs.getString("amount"));
            }






        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new generate_bill().setVisible(true);
    }
}