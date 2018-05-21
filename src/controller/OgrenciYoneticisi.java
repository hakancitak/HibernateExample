
package controller;

import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Ogrenci;
import org.hibernate.Session;

public class OgrenciYoneticisi {
        private JTable ogrTablo;
    private final static String SORGU_KALIP = "from Ogrenci o";
    public Session session;
    private Vector<String> sutunlar = new Vector<>();
    private Vector<Object> satir;
    private DefaultTableModel model;
    public OgrenciYoneticisi(JTable ogrTablo) {
        this.ogrTablo = ogrTablo;
        sutunlar.add("Ogrenci Id");
        sutunlar.add("Ad Soyad");
        sutunlar.add("Şehir");
        sutunlar.add("Tel No");
        model=(DefaultTableModel)ogrTablo.getModel();
        model.setColumnIdentifiers(sutunlar);
    }
    
    public void ogrenciListele(){
        session.beginTransaction();
        List ogrencilerList = session.createQuery(SORGU_KALIP).list();
        session.getTransaction().commit();
        ogrenciGoster(ogrencilerList);
    }
    public void ogrenciEkle(Ogrenci ogrenci){
      
      session.beginTransaction();
      session.save(ogrenci);
      session.getTransaction().commit();
   
    }
    public void ogrenciSil(int ogrencino){
        Ogrenci ogr;
    session.beginTransaction();
    
    ogr=(Ogrenci)session.get(Ogrenci.class, ogrencino);
    session.delete(ogr);
    session.getTransaction().commit();
    }
    public void ogrenciGuncelle(int id,String adsoyad,String sehir,String tel){
     
        session.beginTransaction();
    Ogrenci ogr=(Ogrenci)session.get(Ogrenci.class,id);
    ogr.setAdSoyad(adsoyad);
    ogr.setSehir(sehir);
    ogr.setTel(tel);
    session.update(ogr);
    session.getTransaction().commit();
    
    
    
    }

    public void ogrenciGetir(String aranan, String filtre) {
        String sorguMetin = "";
        if (filtre.equalsIgnoreCase("adSoyad")) {
            sorguMetin = SORGU_KALIP + " where o.adSoyad like '%" + aranan + "%'";
        } else if (filtre.equalsIgnoreCase("sehir")) {
            sorguMetin = SORGU_KALIP + " where o.sehir like '%" + aranan + "%'";
        }
        session.beginTransaction();
        List musterilerList = session.createQuery(sorguMetin).list();
        session.getTransaction().commit();
        ogrenciGoster(musterilerList);

    }

    public void ac() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void kapat() {
        session.close();
    }

    private void ogrenciGoster(List<Ogrenci> ogrencilerList) {
        model.getDataVector().removeAllElements();
        for (Ogrenci gelenOgrenci : ogrencilerList) {
            satir=new Vector();
            satir.add(gelenOgrenci.getOgrenciid());
            satir.add(gelenOgrenci.getAdSoyad());
            satir.add(gelenOgrenci.getSehir());
            satir.add(gelenOgrenci.getTel());
            model.addRow(satir);
        }
    }
}
