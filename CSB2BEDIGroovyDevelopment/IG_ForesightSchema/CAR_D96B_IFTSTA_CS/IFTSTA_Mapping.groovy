import groovy.xml.MarkupBuilder
import java.sql.Connection


String mapping(String inputXmlBody, String[] runtimeParams, Connection conn) {

	cs.b2b.core.mapping.util.MappingUtil util = new cs.b2b.core.mapping.util.MappingUtil();

	/**
	 * Part I: prep handling here, 
	 */
	//inputXmlBody = util.removeBOM(inputXmlBody)

	/**
	 * Part II: get OLL mapping runtime parameters
	 */
	def appSessionId = util.getRuntimeParameter("AppSessionID", runtimeParams);
	def sourceFileName = util.getRuntimeParameter("OriginalSourceFileName", runtimeParams);
	def TP_ID = util.getRuntimeParameter("TP_ID", runtimeParams);
	def MSG_TYPE_ID = util.getRuntimeParameter("MSG_TYPE_ID", runtimeParams);
	def DIR_ID = util.getRuntimeParameter("DIR_ID", runtimeParams);
	def MSG_REQ_ID = util.getRuntimeParameter("MSG_REQ_ID", runtimeParams);

	/**
	 * Part III: read xml and prepare output xml
	 */
	def parser = new XmlParser()
	parser.setNamespaceAware(false)
	//Important: the inputXml is xml root element
	def ContainerMovement = parser.parseText(inputXmlBody);

	
	def writer = new StringWriter()
	def outXml = new MarkupBuilder(new IndentPrinter(new PrintWriter(writer), "", false));
	outXml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")

	/**
	 * Part IV: mapping script start from here
	 */

	TimeZone.setDefault(TimeZone.getTimeZone('GMT+8'))
	def currentSystemDt = new Date()
	//please update foramt here
	def msgFmtId = "X.12"
	def ctrlNos = util.getEDIControlNumber("CARGOSMART", TP_ID, MSG_TYPE_ID, msgFmtId, conn)
	long ediIsaCtrlNumber = ctrlNos[0]
	long ediGroupCtrlNum = ctrlNos[1]

	outXml.'IFTSTA' {
		'UNA' {
			'UNA_01' ''
			'UNA_02' ''
			'UNA_03' ''
			'UNA_04' ''
			'UNA_05' ''
			'UNA_06' ''
		}
		'UNB' {
			'S001_01' {
				'E0001_01' ''
				'E0002_02' ''
			}
			'S002_02' {
				'E0004_01' ''
				'E0007_02' ''
				'E0008_03' ''
			}
			'S003_03' {
				'E0010_01' ''
				'E0007_02' ''
				'E0014_03' ''
			}
			'S004_04' {
				'E0017_01' ''
				'E0019_02' ''
			}
			'E0020_05' ''
			'S005_06' {
				'E0022_01' ''
				'E0025_02' ''
			}
			'E0026_07' ''
			'E0029_08' ''
			'E0031_09' ''
			'E0032_10' ''
			'E0035_11' ''
		}
		'UNG' {
			'E0038_01' ''
			'S006_02' {
				'E0040_01' ''
				'E0007_02' ''
			}
			'S007_03' {
				'E0044_01' ''
				'E0007_02' ''
			}
			'S004_04' {
				'E0017_01' ''
				'E0019_02' ''
			}
			'E0048_05' ''
			'E0051_06' ''
			'S008_07' {
				'E0052_01' ''
				'E0054_02' ''
				'E0057_03' ''
			}
			'E0058_08' ''
		}
		'Group_UNH' {
			'UNH' {
				'E0062_01' ''
				'S009_02' {
					'E0065_01' ''
					'E0052_02' ''
					'E0054_03' ''
					'E0051_04' ''
					'E0057_05' ''
				}
				'E0068_03' ''
				'S010_04' {
					'E0070_01' ''
					'E0073_02' ''
				}
			}
			'BGM' {
				'C002_01' {
					'E1001_01' ''
					'E1131_02' ''
					'E3055_03' ''
					'E1000_04' ''
				}
				'C106_02' {
					'E1004_01' ''
					'E1056_02' ''
					'E1060_03' ''
				}
				'E1225_03' ''
				'E4343_04' ''
			}
			'DTM' {
				'C507_01' {
					'E2005_01' ''
					'E2380_02' ''
					'E2379_03' ''
				}
			}
			'TSR' {
				'C536_01' {
					'E4065_01' ''
					'E1131_02' ''
					'E3055_03' ''
				}
				'C233_02' {
					'E7273_01' ''
					'E1131_02' ''
					'E3055_03' ''
					'E7273_04' ''
					'E1131_05' ''
					'E3055_06' ''
				}
				'C537_03' {
					'E4219_01' ''
					'E1131_02' ''
					'E3055_03' ''
				}
				'C703_04' {
					'E7085_01' ''
					'E1131_02' ''
					'E3055_03' ''
				}
			}
			'Group1_NAD' {
				'NAD' {
					'E3035_01' ''
					'C082_02' {
						'E3039_01' ''
						'E1131_02' ''
						'E3055_03' ''
					}
					'C058_03' {
						'E3124_01' ''
						'E3124_02' ''
						'E3124_03' ''
						'E3124_04' ''
						'E3124_05' ''
					}
					'C080_04' {
						'E3036_01' ''
						'E3036_02' ''
						'E3036_03' ''
						'E3036_04' ''
						'E3036_05' ''
						'E3045_06' ''
					}
					'C059_05' {
						'E3042_01' ''
						'E3042_02' ''
						'E3042_03' ''
						'E3042_04' ''
					}
					'E3164_06' ''
					'E3229_07' ''
					'E3251_08' ''
					'E3207_09' ''
				}
				'Group2_CTA' {
					'CTA' {
						'E3139_01' ''
						'C056_02' {
							'E3413_01' ''
							'E3412_02' ''
						}
					}
					'COM' {
						'C076_01' {
							'E3148_01' ''
							'E3155_02' ''
						}
					}
				}
			}
			'Group3_RFF' {
				'RFF' {
					'C506_01' {
						'E1153_01' ''
						'E1154_02' ''
						'E1156_03' ''
						'E4000_04' ''
					}
				}
				'DTM' {
					'C507_01' {
						'E2005_01' ''
						'E2380_02' ''
						'E2379_03' ''
					}
				}
			}
			'LOC' {
				'E3227_01' ''
				'C517_02' {
					'E3225_01' ''
					'E1131_02' ''
					'E3055_03' ''
					'E3224_04' ''
				}
				'C519_03' {
					'E3223_01' ''
					'E1131_02' ''
					'E3055_03' ''
					'E3222_04' ''
				}
				'C553_04' {
					'E3233_01' ''
					'E1131_02' ''
					'E3055_03' ''
					'E3232_04' ''
				}
				'E5479_05' ''
			}
			'FTX' {
				'E4451_01' ''
				'E4453_02' ''
				'C107_03' {
					'E4441_01' ''
					'E1131_02' ''
					'E3055_03' ''
				}
				'C108_04' {
					'E4440_01' ''
					'E4440_02' ''
					'E4440_03' ''
					'E4440_04' ''
					'E4440_05' ''
				}
				'E3453_05' ''
			}
			'CNT' {
				'C270_01' {
					'E6069_01' ''
					'E6066_02' ''
					'E6411_03' ''
				}
			}
			'Group4_CNI' {
				'CNI' {
					'E1490_01' ''
					'C503_02' {
						'E1004_01' ''
						'E1373_02' ''
						'E1366_03' ''
						'E3453_04' ''
					}
					'E1312_03' ''
				}
				'LOC' {
					'E3227_01' ''
					'C517_02' {
						'E3225_01' ''
						'E1131_02' ''
						'E3055_03' ''
						'E3224_04' ''
					}
					'C519_03' {
						'E3223_01' ''
						'E1131_02' ''
						'E3055_03' ''
						'E3222_04' ''
					}
					'C553_04' {
						'E3233_01' ''
						'E1131_02' ''
						'E3055_03' ''
						'E3232_04' ''
					}
					'E5479_05' ''
				}
				'CNT' {
					'C270_01' {
						'E6069_01' ''
						'E6066_02' ''
						'E6411_03' ''
					}
				}
				'Group5_STS' {
					'STS' {
						'C601_01' {
							'E9015_01' ''
							'E1131_02' ''
							'E3055_03' ''
						}
						'C555_02' {
							'E9011_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9010_04' ''
						}
						'C556_03' {
							'E9013_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9012_04' ''
						}
						'C556_04' {
							'E9013_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9012_04' ''
						}
						'C556_05' {
							'E9013_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9012_04' ''
						}
						'C556_06' {
							'E9013_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9012_04' ''
						}
						'C556_07' {
							'E9013_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E9012_04' ''
						}
					}
					'RFF' {
						'C506_01' {
							'E1153_01' ''
							'E1154_02' ''
							'E1156_03' ''
							'E4000_04' ''
						}
					}
					'DTM' {
						'C507_01' {
							'E2005_01' ''
							'E2380_02' ''
							'E2379_03' ''
						}
					}
					'DOC' {
						'C002_01' {
							'E1001_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E1000_04' ''
						}
						'C503_02' {
							'E1004_01' ''
							'E1373_02' ''
							'E1366_03' ''
							'E3453_04' ''
						}
						'E3153_03' ''
						'E1220_04' ''
						'E1218_05' ''
					}
					'FTX' {
						'E4451_01' ''
						'E4453_02' ''
						'C107_03' {
							'E4441_01' ''
							'E1131_02' ''
							'E3055_03' ''
						}
						'C108_04' {
							'E4440_01' ''
							'E4440_02' ''
							'E4440_03' ''
							'E4440_04' ''
							'E4440_05' ''
						}
						'E3453_05' ''
					}
					'NAD' {
						'E3035_01' ''
						'C082_02' {
							'E3039_01' ''
							'E1131_02' ''
							'E3055_03' ''
						}
						'C058_03' {
							'E3124_01' ''
							'E3124_02' ''
							'E3124_03' ''
							'E3124_04' ''
							'E3124_05' ''
						}
						'C080_04' {
							'E3036_01' ''
							'E3036_02' ''
							'E3036_03' ''
							'E3036_04' ''
							'E3036_05' ''
							'E3045_06' ''
						}
						'C059_05' {
							'E3042_01' ''
							'E3042_02' ''
							'E3042_03' ''
							'E3042_04' ''
						}
						'E3164_06' ''
						'E3229_07' ''
						'E3251_08' ''
						'E3207_09' ''
					}
					'LOC' {
						'E3227_01' ''
						'C517_02' {
							'E3225_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E3224_04' ''
						}
						'C519_03' {
							'E3223_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E3222_04' ''
						}
						'C553_04' {
							'E3233_01' ''
							'E1131_02' ''
							'E3055_03' ''
							'E3232_04' ''
						}
						'E5479_05' ''
					}
					'PCI' {
						'E4233_01' ''
						'C210_02' {
							'E7102_01' ''
							'E7102_02' ''
							'E7102_03' ''
							'E7102_04' ''
							'E7102_05' ''
							'E7102_06' ''
							'E7102_07' ''
							'E7102_08' ''
							'E7102_09' ''
							'E7102_10' ''
						}
						'E8275_03' ''
						'C827_04' {
							'E7511_01' ''
							'E1131_02' ''
							'E3055_03' ''
						}
					}
					'Group6_TDT' {
						'TDT' {
							'E8051_01' ''
							'E8028_02' ''
							'C220_03' {
								'E8067_01' ''
								'E8066_02' ''
							}
							'C228_04' {
								'E8179_01' ''
								'E8178_02' ''
							}
							'C040_05' {
								'E3127_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3128_04' ''
							}
							'E8101_06' ''
							'C401_07' {
								'E8457_01' ''
								'E8459_02' ''
								'E7130_03' ''
							}
							'C222_08' {
								'E8213_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E8212_04' ''
								'E8453_05' ''
							}
							'E8281_09' ''
						}
						'RFF' {
							'C506_01' {
								'E1153_01' ''
								'E1154_02' ''
								'E1156_03' ''
								'E4000_04' ''
							}
						}
						'LOC' {
							'E3227_01' ''
							'C517_02' {
								'E3225_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3224_04' ''
							}
							'C519_03' {
								'E3223_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3222_04' ''
							}
							'C553_04' {
								'E3233_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3232_04' ''
							}
							'E5479_05' ''
						}
						'DTM' {
							'C507_01' {
								'E2005_01' ''
								'E2380_02' ''
								'E2379_03' ''
							}
						}
					}
					'Group7_EQD' {
						'EQD' {
							'E8053_01' ''
							'C237_02' {
								'E8260_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3207_04' ''
							}
							'C224_03' {
								'E8155_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E8154_04' ''
							}
							'E8077_04' ''
							'E8249_05' ''
							'E8169_06' ''
						}
						'MEA' {
							'E6311_01' ''
							'C502_02' {
								'E6313_01' ''
								'E6321_02' ''
								'E6155_03' ''
								'E6154_04' ''
							}
							'C174_03' {
								'E6411_01' ''
								'E6314_02' ''
								'E6162_03' ''
								'E6152_04' ''
								'E6432_05' ''
							}
							'E7383_04' ''
						}
						'DIM' {
							'E6145_01' ''
							'C211_02' {
								'E6411_01' ''
								'E6168_02' ''
								'E6140_03' ''
								'E6008_04' ''
							}
						}
						'SEL' {
							'E9308_01' ''
							'C215_02' {
								'E9303_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E9302_04' ''
							}
							'E4517_03' ''
						}
						'TPL' {
							'C222_01' {
								'E8213_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E8212_04' ''
								'E8453_05' ''
							}
						}
						'Group8_EQA' {
							'EQA' {
								'E8053_01' ''
								'C237_02' {
									'E8260_01' ''
									'E1131_02' ''
									'E3055_03' ''
									'E3207_04' ''
								}
							}
							'SEL' {
								'E9308_01' ''
								'C215_02' {
									'E9303_01' ''
									'E1131_02' ''
									'E3055_03' ''
									'E9302_04' ''
								}
								'E4517_03' ''
							}
						}
					}
					'Group9_GID' {
						'GID' {
							'E1496_01' ''
							'C213_02' {
								'E7224_01' ''
								'E7065_02' ''
								'E1131_03' ''
								'E3055_04' ''
								'E7064_05' ''
							}
							'C213_03' {
								'E7224_01' ''
								'E7065_02' ''
								'E1131_03' ''
								'E3055_04' ''
								'E7064_05' ''
							}
							'C213_04' {
								'E7224_01' ''
								'E7065_02' ''
								'E1131_03' ''
								'E3055_04' ''
								'E7064_05' ''
							}
						}
						'HAN' {
							'C524_01' {
								'E4079_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E4078_04' ''
							}
							'C218_02' {
								'E7419_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E7418_04' ''
							}
						}
						'SGP' {
							'C237_01' {
								'E8260_01' ''
								'E1131_02' ''
								'E3055_03' ''
								'E3207_04' ''
							}
							'E7224_02' ''
						}
						'DGS' {
							'E8273_01' ''
							'C205_02' {
								'E8351_01' ''
								'E8078_02' ''
								'E8092_03' ''
							}
							'C234_03' {
								'E7124_01' ''
								'E7088_02' ''
							}
							'C223_04' {
								'E7106_01' ''
								'E6411_02' ''
							}
							'E8339_05' ''
							'E8364_06' ''
							'E8410_07' ''
							'E8126_08' ''
							'C235_09' {
								'E8158_01' ''
								'E8186_02' ''
							}
							'C236_10' {
								'E8246_01' ''
								'E8246_02' ''
								'E8246_03' ''
							}
							'E8255_11' ''
							'E8325_12' ''
							'E8211_13' ''
						}
						'FTX' {
							'E4451_01' ''
							'E4453_02' ''
							'C107_03' {
								'E4441_01' ''
								'E1131_02' ''
								'E3055_03' ''
							}
							'C108_04' {
								'E4440_01' ''
								'E4440_02' ''
								'E4440_03' ''
								'E4440_04' ''
								'E4440_05' ''
							}
							'E3453_05' ''
						}
						'Group10_MEA' {
							'MEA' {
								'E6311_01' ''
								'C502_02' {
									'E6313_01' ''
									'E6321_02' ''
									'E6155_03' ''
									'E6154_04' ''
								}
								'C174_03' {
									'E6411_01' ''
									'E6314_02' ''
									'E6162_03' ''
									'E6152_04' ''
									'E6432_05' ''
								}
								'E7383_04' ''
							}
							'EQN' {
								'C523_01' {
									'E6350_01' ''
									'E6353_02' ''
								}
							}
						}
						'Group11_DIM' {
							'DIM' {
								'E6145_01' ''
								'C211_02' {
									'E6411_01' ''
									'E6168_02' ''
									'E6140_03' ''
									'E6008_04' ''
								}
							}
							'EQN' {
								'C523_01' {
									'E6350_01' ''
									'E6353_02' ''
								}
							}
						}
						'Group12_PCI' {
							'PCI' {
								'E4233_01' ''
								'C210_02' {
									'E7102_01' ''
									'E7102_02' ''
									'E7102_03' ''
									'E7102_04' ''
									'E7102_05' ''
									'E7102_06' ''
									'E7102_07' ''
									'E7102_08' ''
									'E7102_09' ''
									'E7102_10' ''
								}
								'E8275_03' ''
								'C827_04' {
									'E7511_01' ''
									'E1131_02' ''
									'E3055_03' ''
								}
							}
							'GIN' {
								'E7405_01' ''
								'C208_02' {
									'E7402_01' ''
									'E7402_02' ''
								}
								'C208_03' {
									'E7402_01' ''
									'E7402_02' ''
								}
								'C208_04' {
									'E7402_01' ''
									'E7402_02' ''
								}
								'C208_05' {
									'E7402_01' ''
									'E7402_02' ''
								}
								'C208_06' {
									'E7402_01' ''
									'E7402_02' ''
								}
							}
						}
					}
				}
			}
			'UNT' {
				'E0074_01' ''
				'E0062_02' ''
			}
		}
		'UNE' {
			'E0060_01' ''
			'E0048_02' ''
		}
		'UNZ' {
			'E0036_01' ''
			'E0020_02' ''
		}
	}
	
}
