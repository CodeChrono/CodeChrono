package com.codechrono.idea.plugin.dao;

import com.codechrono.idea.plugin.entity.KeyChrono;
import java.sql.Connection;
/**
 * @author Codechrono
 */
public interface KeyChronoDao extends CommonDao<KeyChrono> {

    KeyChrono findByKeyCode(Connection conn, String keyCode);

}
