package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Reiziger;

import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(LocalDate date);
    List<Reiziger> findAll();
}
