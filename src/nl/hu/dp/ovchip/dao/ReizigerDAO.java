package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Reiziger;

import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
    public Boolean save(Reiziger reiziger);
    public Boolean update(Reiziger reiziger);
    public Boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbdatum(LocalDate date);
    public List<Reiziger> findAll();
}
