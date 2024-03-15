package com.example.contratsassurance;

import com.example.contratsassurance.Entities.ContratAssurance;
import com.example.contratsassurance.Repository.ContratAssuranceRepository;
import org.apache.poi.ss.usermodel.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ExcelReader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    private final ContratAssuranceRepository contratAssuranceRepository;

    @Autowired
    public ExcelReader(ContratAssuranceRepository contratAssuranceRepository) {
        this.contratAssuranceRepository = contratAssuranceRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        try (FileInputStream file = new FileInputStream(new File("C:\\Users\\octanet\\Downloads\\APPLICATION-CONTENU.xlsx"))) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            LOGGER.info("Starting to read and save data from Excel file...");

            // Iterate through each row starting from B6 (assuming data starts from B6)
            for (int i = 5; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    LOGGER.warn("Row is null at index {}", i);
                    continue; // Skip to next iteration
                }

                // Create ContratAssurance object for each row
                ContratAssurance contratAssurance = new ContratAssurance();

                // Read and populate attributes of ContratAssurance object from cells
                Cell compagnieCell = row.getCell(1); // Compagnie d'assurance
                Cell typeAssuranceCell = row.getCell(2); // Type de l'assurance
                Cell nomAssureCell = row.getCell(3); // Nom de l'assuré
                Cell numeroSouscriptionCell = row.getCell(4); // Numéro de souscription
                Cell beneficiaire1Cell = row.getCell(5); // Bénéficiaire 1
                Cell regionCell = row.getCell(6); // Région
                Cell servicesCell = row.getCell(7); // Services
                Cell plafondCell = row.getCell(8); // Plafond
                Cell nombreDeclarationsCell = row.getCell(9); // Nombre de déclaration

                // Check if any cell is null or empty
                if (compagnieCell == null || compagnieCell.getCellType() != CellType.STRING || compagnieCell.getStringCellValue().isEmpty() ||
                        typeAssuranceCell == null || typeAssuranceCell.getCellType() != CellType.STRING || typeAssuranceCell.getStringCellValue().isEmpty() ||
                        nomAssureCell == null || nomAssureCell.getCellType() != CellType.STRING || nomAssureCell.getStringCellValue().isEmpty() ||
                        beneficiaire1Cell == null || beneficiaire1Cell.getCellType() != CellType.STRING || beneficiaire1Cell.getStringCellValue().isEmpty() ||
                        regionCell == null || regionCell.getCellType() != CellType.STRING || regionCell.getStringCellValue().isEmpty() ||
                        servicesCell == null || servicesCell.getCellType() != CellType.STRING || servicesCell.getStringCellValue().isEmpty() ||
                        plafondCell == null || plafondCell.getCellType() != CellType.STRING || plafondCell.getStringCellValue().isEmpty() ||
                        nombreDeclarationsCell == null || nombreDeclarationsCell.getCellType() != CellType.STRING || nombreDeclarationsCell.getStringCellValue().isEmpty()) {
                    // Skip this row if any cell is null or empty
                    LOGGER.warn("Skipping empty or incomplete contract data for row {}", i + 1);
                    continue; // Skip to next row
                }

                // Populate ContratAssurance object with data
                contratAssurance.setCompagnieAssurance(compagnieCell.getStringCellValue());
                contratAssurance.setTypeAssurance(typeAssuranceCell.getStringCellValue());
                contratAssurance.setNomAssure(nomAssureCell.getStringCellValue());
                contratAssurance.setBeneficiaire1(beneficiaire1Cell.getStringCellValue());
                contratAssurance.setRegion(regionCell.getStringCellValue());
                contratAssurance.setServices(servicesCell.getStringCellValue());
                contratAssurance.setPlafond(plafondCell.getStringCellValue());
                contratAssurance.setNombreDeclarations(nombreDeclarationsCell.getStringCellValue());

                // Handle Numéro de souscription cell
                if (numeroSouscriptionCell != null) {
                    if (numeroSouscriptionCell.getCellType() == CellType.NUMERIC) {
                        double numeroSouscriptionValue = numeroSouscriptionCell.getNumericCellValue();
                        contratAssurance.setNumeroSouscription((int) numeroSouscriptionValue);
                    } else if (numeroSouscriptionCell.getCellType() == CellType.STRING) {
                        String numeroSouscriptionCellValue = numeroSouscriptionCell.getStringCellValue();
                        if (!numeroSouscriptionCellValue.isEmpty()) {
                            try {
                                int numeroSouscriptionValue = Integer.parseInt(numeroSouscriptionCellValue);
                                contratAssurance.setNumeroSouscription(numeroSouscriptionValue);
                            } catch (NumberFormatException e) {
                                LOGGER.warn("Invalid number format for Numéro de souscription at row {}: {}", i + 1, numeroSouscriptionCellValue);
                            }
                        } else {
                            LOGGER.warn("Empty value for Numéro de souscription at row {}", i + 1);
                        }
                    } else {
                        LOGGER.warn("Unsupported cell type for Numéro de souscription at row {}: {}", i + 1, numeroSouscriptionCell.getCellType());
                    }
                } else {
                    LOGGER.warn("Numéro de souscription cell is null at row {}", i + 1);
                }

                // Save the contract to MongoDB using the repository
                contratAssuranceRepository.save(contratAssurance);
                LOGGER.info("Saved data for row: {}", i + 1);
            }

            LOGGER.info("Data from Excel file saved successfully.");
        } catch (IOException e) {
            LOGGER.error("Error occurred while reading or saving data from Excel file:", e);
        }
    }





}
