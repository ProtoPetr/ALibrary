package com.epam.service;

import com.epam.dao.UserDao;
import com.epam.dao.mysql.MysqlUserDao;
import com.epam.entity.User;
import com.epam.serviсe.ServiceFactory;
import com.epam.serviсe.UserService;
import com.epam.serviсe.mysql.MySqlUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;

public class MySqlUserServiceTest {

    @Test
    public void findUserByIdShouldReturnTrue() throws Exception {
        UserDao userDao = Mockito.mock(MysqlUserDao.class);
        Mockito.when(userDao.findById(any(Connection.class), anyString(), anyLong())).thenReturn(
                new User("Артур", "Пирожков", "Arch", "888", "arch@gmail.com"));

        UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();
        assertEquals(new User("Артур", "Пирожков", "Arch", "888", "arch@gmail.com"),
                uss.findUserById(1));
    }
}
