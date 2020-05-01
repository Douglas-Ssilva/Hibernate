package br.com.teste;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.com.caelum.JpaConfigurator;

public class TestePoolConnections {
	
	public static void main(String[] args) throws PropertyVetoException, SQLException {
		
		ComboPooledDataSource comboPooledDataSource= (ComboPooledDataSource) new JpaConfigurator().getDataSource();
		
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		comboPooledDataSource.getConnection();
		
		System.out.println("Connections in use: " + comboPooledDataSource.getNumBusyConnections());
		System.out.println("Connections ociosas: " + comboPooledDataSource.getNumIdleConnections());
		
		
	}

}
