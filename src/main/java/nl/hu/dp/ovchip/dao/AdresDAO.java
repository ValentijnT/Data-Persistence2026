package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
    Adres findByReiziger(Reiziger r);
    List<Adres> findAll();
}
