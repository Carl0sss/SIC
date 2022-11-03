/*
 Navicat Premium Data Transfer

 Source Server         : My_sql
 Source Server Type    : MySQL
 Source Server Version : 100425
 Source Host           : localhost:3306
 Source Schema         : contabilidad

 Target Server Type    : MySQL
 Target Server Version : 100425
 File Encoding         : 65001

 Date: 03/11/2022 16:37:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas`  (
  `COD_CUENTA` int NOT NULL,
  `ID_SALDO` int NOT NULL,
  `NOMBRE_CUENTA` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`COD_CUENTA`) USING BTREE,
  INDEX `ID_SALDO`(`ID_SALDO` ASC) USING BTREE,
  CONSTRAINT `cuentas_ibfk_1` FOREIGN KEY (`ID_SALDO`) REFERENCES `saldo` (`ID_SALDO`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cuentas
-- ----------------------------
INSERT INTO `cuentas` VALUES (1, 1, 'Activo');
INSERT INTO `cuentas` VALUES (2, 2, 'Pasivo');
INSERT INTO `cuentas` VALUES (3, 2, 'Patrimonio');
INSERT INTO `cuentas` VALUES (4, 1, 'Cuentas De Resultado Deudor');
INSERT INTO `cuentas` VALUES (5, 2, 'Cuentas De Resultado Acreedor');
INSERT INTO `cuentas` VALUES (6, 2, 'Cuenta Liquidadora de Resultado');
INSERT INTO `cuentas` VALUES (11, 1, 'Activo Circulante');
INSERT INTO `cuentas` VALUES (12, 1, 'Activos diferidos');
INSERT INTO `cuentas` VALUES (13, 1, 'Otros Activos');
INSERT INTO `cuentas` VALUES (14, 1, 'Activo no Circulante');
INSERT INTO `cuentas` VALUES (21, 2, 'Pasivo Circulante');
INSERT INTO `cuentas` VALUES (22, 2, 'Pasivo no Circulante');
INSERT INTO `cuentas` VALUES (31, 2, 'Capital contable');
INSERT INTO `cuentas` VALUES (41, 1, 'Costos y Gastos de operaciones');
INSERT INTO `cuentas` VALUES (51, 2, 'Ingresos de operación ');
INSERT INTO `cuentas` VALUES (111, 1, 'Caja ');
INSERT INTO `cuentas` VALUES (112, 1, 'Bancos');
INSERT INTO `cuentas` VALUES (113, 1, 'Cuentas y documentos por cobrar');
INSERT INTO `cuentas` VALUES (114, 1, 'Deudores varios');
INSERT INTO `cuentas` VALUES (115, 1, 'Estimación por cuentas incobrables');
INSERT INTO `cuentas` VALUES (116, 1, 'Inventario de mercaderías');
INSERT INTO `cuentas` VALUES (117, 1, 'IVA Credito Fiscal');
INSERT INTO `cuentas` VALUES (121, 1, 'Gastos pagados por anticipado');
INSERT INTO `cuentas` VALUES (131, 1, 'Papeleria y utiles');
INSERT INTO `cuentas` VALUES (141, 1, 'Bienes Inmuebles');
INSERT INTO `cuentas` VALUES (142, 1, 'Bienes inmuebles');
INSERT INTO `cuentas` VALUES (143, 1, 'Depreciacion acumulada');
INSERT INTO `cuentas` VALUES (144, 1, 'Reevaluaciones ');
INSERT INTO `cuentas` VALUES (145, 1, 'Activos intangibles');
INSERT INTO `cuentas` VALUES (146, 1, 'Amortizaciones Acumuladas');
INSERT INTO `cuentas` VALUES (147, 1, 'Inversiones y valores');
INSERT INTO `cuentas` VALUES (211, 2, 'Prestamos y sobregiros bancarios ');
INSERT INTO `cuentas` VALUES (212, 2, 'Cuentas y Documentos por pagar');
INSERT INTO `cuentas` VALUES (213, 2, 'Acreedores varios');
INSERT INTO `cuentas` VALUES (214, 2, 'Intereses por pagar');
INSERT INTO `cuentas` VALUES (215, 2, 'IVA Debito Fiscal');
INSERT INTO `cuentas` VALUES (216, 2, 'Anticipo de clientes');
INSERT INTO `cuentas` VALUES (221, 2, 'Cuentas y Documentos por pagar a Largo Plazo');
INSERT INTO `cuentas` VALUES (311, 2, 'Capital');
INSERT INTO `cuentas` VALUES (312, 2, 'Reservas');
INSERT INTO `cuentas` VALUES (411, 1, 'Costo de ventas de mercaderías');
INSERT INTO `cuentas` VALUES (412, 1, 'Compra');
INSERT INTO `cuentas` VALUES (413, 1, 'Gastos sobre compras ');
INSERT INTO `cuentas` VALUES (414, 1, 'Rebajas y Devoluciones en ventas');
INSERT INTO `cuentas` VALUES (415, 1, 'Gastos de administracion');
INSERT INTO `cuentas` VALUES (416, 1, 'Gastos de ventas ');
INSERT INTO `cuentas` VALUES (417, 1, 'Gastos Financieros');
INSERT INTO `cuentas` VALUES (418, 1, 'Otros Gastos');
INSERT INTO `cuentas` VALUES (511, 2, 'Ingresos por servicio');
INSERT INTO `cuentas` VALUES (512, 2, 'Ventas');
INSERT INTO `cuentas` VALUES (513, 2, 'Rebajas y Devoluciones sobre Compras');
INSERT INTO `cuentas` VALUES (611, 2, 'Utilidad del Ejercicio');
INSERT INTO `cuentas` VALUES (1121, 1, 'Banco Agrícola');
INSERT INTO `cuentas` VALUES (1122, 1, 'Banco Salvadoreno');
INSERT INTO `cuentas` VALUES (1123, 1, 'Banco Cuscatlan');
INSERT INTO `cuentas` VALUES (1211, 1, 'Primas de seguros');
INSERT INTO `cuentas` VALUES (1212, 1, 'Alquileres pagados por anticipado');
INSERT INTO `cuentas` VALUES (1213, 1, 'Publicidad pagados por anticipado');
INSERT INTO `cuentas` VALUES (1214, 1, 'Impuestos pagados por anticipado');
INSERT INTO `cuentas` VALUES (1411, 1, 'Terrenos');
INSERT INTO `cuentas` VALUES (1412, 1, 'Edificaciones');
INSERT INTO `cuentas` VALUES (1413, 1, 'Instalaciones');
INSERT INTO `cuentas` VALUES (1421, 1, 'Mobiliario y Equipo?\r');
INSERT INTO `cuentas` VALUES (1422, 1, 'Vehiculos');
INSERT INTO `cuentas` VALUES (1423, 1, 'Herramientas y accesorios');
INSERT INTO `cuentas` VALUES (1451, 1, 'Marcas y patentes?\r');
INSERT INTO `cuentas` VALUES (1452, 1, 'Licencias y concesiones');
INSERT INTO `cuentas` VALUES (6111, 2, 'Perdidas y ganancias');

-- ----------------------------
-- Table structure for partida
-- ----------------------------
DROP TABLE IF EXISTS `partida`;
CREATE TABLE `partida`  (
  `NUM_PARTIDA` int NOT NULL AUTO_INCREMENT,
  `FECHA` date NULL DEFAULT NULL,
  `DESCRIPCION` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`NUM_PARTIDA`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of partida
-- ----------------------------
INSERT INTO `partida` VALUES (1, '2022-11-03', 'Partida inicio periodo contable');

-- ----------------------------
-- Table structure for partida_detalle
-- ----------------------------
DROP TABLE IF EXISTS `partida_detalle`;
CREATE TABLE `partida_detalle`  (
  `DEBE` decimal(10, 0) NULL DEFAULT NULL,
  `HABER` decimal(10, 0) NULL DEFAULT NULL,
  `ID` int NOT NULL AUTO_INCREMENT,
  `COD_CUENTA` int NULL DEFAULT NULL,
  `NUM_PARTIDA` int NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `NUM_PARTIDA`(`NUM_PARTIDA` ASC) USING BTREE,
  INDEX `COD_CUENTA`(`COD_CUENTA` ASC) USING BTREE,
  CONSTRAINT `partida_detalle_ibfk_1` FOREIGN KEY (`NUM_PARTIDA`) REFERENCES `partida` (`NUM_PARTIDA`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `partida_detalle_ibfk_2` FOREIGN KEY (`COD_CUENTA`) REFERENCES `cuentas` (`COD_CUENTA`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of partida_detalle
-- ----------------------------
INSERT INTO `partida_detalle` VALUES (10000, 0, 1, 111, 1);
INSERT INTO `partida_detalle` VALUES (20000, 0, 2, 116, 1);
INSERT INTO `partida_detalle` VALUES (1000, 0, 3, 113, 1);
INSERT INTO `partida_detalle` VALUES (0, 5000, 4, 212, 1);
INSERT INTO `partida_detalle` VALUES (0, 26000, 5, 311, 1);

-- ----------------------------
-- Table structure for planilla
-- ----------------------------
DROP TABLE IF EXISTS `planilla`;
CREATE TABLE `planilla`  (
  `NOMBRE_EMPLEADO` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `OCUPACION` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `HORAS_TRABAJADAS` decimal(10, 0) NULL DEFAULT NULL,
  `SALARIO_HORA` decimal(10, 0) NULL DEFAULT NULL,
  `DIAS_TRABAJADOS` int NULL DEFAULT NULL,
  `ID_TRABAJADOR` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID_TRABAJADOR`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of planilla
-- ----------------------------
INSERT INTO `planilla` VALUES ('Leonardo Efigenio                                                                                                                                                                                                                                               ', 'Gerente\n                                                                                                                                                                                                                                                        ', 7, 25, 30, 1);
INSERT INTO `planilla` VALUES ('Carlos Rafaelano\n                                                                                                                                                                                                                                               ', 'Empleado\n                                                                                                                                                                                                                                                       ', 8, 20, 30, 2);
INSERT INTO `planilla` VALUES ('Aura Chavez                                                                                                                                                                                                                                                     ', 'Empelado                                                                                                                                                                                                                                                        ', 8, 20, 30, 3);
INSERT INTO `planilla` VALUES ('Carl Johnson                                                                                                                                                                                                                                                    ', 'Empleado                                                                                                                                                                                                                                                        ', 8, 20, 30, 4);
INSERT INTO `planilla` VALUES ('﻿Yulia Romanova                                                                                                                                                                                                                                                 ', 'Empleado                                                                                                                                                                                                                                                        ', 8, 20, 30, 5);
INSERT INTO `planilla` VALUES ('Pedro Pablo Armilio Maravilla', 'Tecnico de limpieza', 8, 10, 30, 6);

-- ----------------------------
-- Table structure for saldo
-- ----------------------------
DROP TABLE IF EXISTS `saldo`;
CREATE TABLE `saldo`  (
  `ID_SALDO` int NOT NULL,
  `TIPO_SALDO` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_SALDO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of saldo
-- ----------------------------
INSERT INTO `saldo` VALUES (1, 'Deudor');
INSERT INTO `saldo` VALUES (2, 'Acreedor');

SET FOREIGN_KEY_CHECKS = 1;
