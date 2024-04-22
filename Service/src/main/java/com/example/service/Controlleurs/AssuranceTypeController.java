package com.example.service.Controlleurs;

import com.example.service.Models.AssuranceType;
import com.example.service.Services.AssuranceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assurance-types")
public class AssuranceTypeController {

    @Autowired
    private AssuranceTypeService assuranceTypeService;

    @GetMapping
    public List<AssuranceType> getAllAssuranceTypes() {
        return assuranceTypeService.getAllAssuranceTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssuranceType> getAssuranceTypeById(@PathVariable String id) {
        Optional<AssuranceType> assuranceType = assuranceTypeService.getAssuranceTypeById(id);
        return assuranceType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public AssuranceType createAssuranceType(@RequestBody AssuranceType assuranceType) {
        return assuranceTypeService.createAssuranceType(assuranceType);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AssuranceType> updateAssuranceType(@PathVariable String id, @RequestBody AssuranceType assuranceType) {
        return ResponseEntity.ok(assuranceTypeService.updateAssuranceType(id, assuranceType));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAssuranceType(@PathVariable String id) {
        assuranceTypeService.deleteAssuranceType(id);
        return ResponseEntity.ok().build();
    }
}
