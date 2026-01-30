package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);

    List<OVChipkaart> findByReiziger(Reiziger reiziger);
    OVChipkaart findById(int id);
    List<OVChipkaart> findAll();
}
