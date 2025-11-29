package com.restaurant.inventorysystem.service.impl;

import com.restaurant.inventorysystem.dto.*;
import com.restaurant.inventorysystem.entity.*;
import com.restaurant.inventorysystem.exception.DuplicateResourceException;
import com.restaurant.inventorysystem.exception.ResourceNotFoundException;
import com.restaurant.inventorysystem.repository.*;
import com.restaurant.inventorysystem.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Ram Choudhary
 * Date: 07-Nov-2025
 * Description: Implements Recipe CRUD. Saves master and all xrefs in one go.
 * Since: 07-Nov-2025
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeMasterRepository recipeRepo;
    private final RecipeIngredientXrefRepository xrefRepo;
    private final IngredientsRepository ingredientRepo;
    private final UserRepository userRepo;

    public RecipeServiceImpl(RecipeMasterRepository recipeRepo,
                             RecipeIngredientXrefRepository xrefRepo,
                             IngredientsRepository ingredientRepo,
                             UserRepository userRepo) {
        this.recipeRepo = recipeRepo;
        this.xrefRepo = xrefRepo;
        this.ingredientRepo = ingredientRepo;
        this.userRepo = userRepo;
    }

    /**
     * Author: Ram Choudhary
     * Date: 07-Nov-2025
     *
     * Description:
     * This method handles the complete creation of a recipe, including:
     * 1. Validating incoming RecipeRequestDTO (master + xref list)
     * 2. Performing duplicate checks for recipe code
     * 3. Mapping RecipeMasterDTO → RecipeMaster entity
     * 4. Saving the recipe master into the database
     * 5. Mapping each RecipeIngredientXrefDTO → RecipeIngredientXref entity
     * 6. Assigning recipe FK, ingredient FK, audit fields
     * 7. Saving all ingredient xref rows linked with the recipe
     *
     * This method ensures:
     * - All data is saved in a single transactional block
     * - No partial data is saved if an error occurs
     * - Correct audit handling with createdDate & createdBy
     *
     * @param request Composite DTO (master + xrefs) coming from frontend
     * @return Newly saved RecipeMaster entity
     * @since 07-Nov-2025
     */
    @Override
    @Transactional
    public RecipeMaster saveRecipe(RecipeRequestDTO request) {

        // ----------------- VALIDATION -----------------
        if (request == null || request.getRecipeMasterDTO() == null) {
            throw new IllegalArgumentException("Master data is missing");
        }

        RecipeMasterDTO recipeMasterDTO = request.getRecipeMasterDTO();

        if (recipeMasterDTO.getRecipeCode() == null || recipeMasterDTO.getRecipeCode().isBlank()) {
            throw new IllegalArgumentException("RecipeCode is required");
        }

        // Duplicate check
        if (recipeRepo.existsByRecipeCodeIgnoreCase(recipeMasterDTO.getRecipeCode())) {
            throw new DuplicateResourceException("Recipe code already exists: " + recipeMasterDTO.getRecipeCode());
        }

        // ----------------- MAP DTO TO MASTER ENTITY -----------------
        RecipeMaster recipeMaster = new RecipeMaster();
        recipeMaster.setRecipeCode(recipeMasterDTO.getRecipeCode());
        recipeMaster.setRecipeName(recipeMasterDTO.getRecipeName());
        recipeMaster.setRecipeType(recipeMasterDTO.getRecipeType());
        recipeMaster.setDescription(recipeMasterDTO.getDescription());
        recipeMaster.setSellingPrice(recipeMasterDTO.getSellingPrice());
        recipeMaster.setTotalCost(recipeMasterDTO.getTotalCost());
        recipeMaster.setPreparationTime(recipeMasterDTO.getPreparationTime());
        recipeMaster.setActiveFlag(recipeMasterDTO.getActiveFlag());
        recipeMaster.setEnableFlag(recipeMasterDTO.getEnableFlag());
        recipeMaster.setCreatedDate(LocalDateTime.now());
        recipeMaster.setModifiedDate(recipeMasterDTO.getModifiedDate());

        if (recipeMasterDTO.getCreatedById() != null) {
            recipeMaster.setCreatedBy(
                    userRepo.findById(recipeMasterDTO.getCreatedById())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeMasterDTO.getCreatedById()))
            );
        }

        if (recipeMasterDTO.getModifiedById() != null) {
            recipeMaster.setModifiedBy(
                    userRepo.findById(recipeMasterDTO.getModifiedById())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeMasterDTO.getModifiedById()))
            );
        }

        // Save master
        RecipeMaster savedMaster = recipeRepo.save(recipeMaster);

        // ----------------- SAVE XREF LIST -----------------
        if (request.getRecipeIngredientXrefDTO() != null && !request.getRecipeIngredientXrefDTO().isEmpty()) {

            for (RecipeIngredientXrefDTO recipeIngredientXrefDTO : request.getRecipeIngredientXrefDTO()) {

                RecipeIngredientXref recipeIngredientXref = new RecipeIngredientXref();
                recipeIngredientXref.setRecipe(savedMaster);     // FK

                // ingredient FK
                recipeIngredientXref.setIngredient(
                        ingredientRepo.findById(recipeIngredientXrefDTO.getIngredientId())
                                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + recipeIngredientXrefDTO.getIngredientId()))
                );

                recipeIngredientXref.setUom(recipeIngredientXrefDTO.getUom());
                recipeIngredientXref.setSmallerUnit(recipeIngredientXrefDTO.getSmallerUnit());
                recipeIngredientXref.setBaseUnitValue(recipeIngredientXrefDTO.getBaseUnitValue());
                recipeIngredientXref.setQuantityValue(recipeIngredientXrefDTO.getQuantityValue());
                recipeIngredientXref.setRemarks(recipeIngredientXrefDTO.getRemarks());
                recipeIngredientXref.setActiveFlag(recipeIngredientXrefDTO.getActiveFlag());
                recipeIngredientXref.setEnableFlag(recipeIngredientXrefDTO.getEnableFlag());
                recipeIngredientXref.setCreatedDate(LocalDateTime.now());
                recipeIngredientXref.setModifiedDate(recipeIngredientXrefDTO.getModifiedDate());

                if (recipeIngredientXrefDTO.getCreatedById() != null) {
                    recipeIngredientXref.setCreatedBy(
                            userRepo.findById(recipeIngredientXrefDTO.getCreatedById())
                                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeIngredientXrefDTO.getCreatedById()))
                    );
                }

                if (recipeIngredientXrefDTO.getModifiedById() != null) {
                    recipeIngredientXref.setModifiedBy(
                            userRepo.findById(recipeIngredientXrefDTO.getModifiedById())
                                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeIngredientXrefDTO.getModifiedById()))
                    );
                }

                xrefRepo.save(recipeIngredientXref);
            }
        }

        return savedMaster;
    }


    /**
     * Author: Ram Choudhary
     * Date: 07-Nov-2025
     *
     * Description:
     * This method updates an existing recipe and completely replaces its ingredient xref list.
     *
     * Process:
     * 1. Validate incoming request & check master section exists
     * 2. Fetch the existing RecipeMaster by ID
     * 3. Validate updated fields and check for duplicate recipe code (excluding current recipe)
     * 4. Map all updated fields from DTO → entity
     * 5. Update audit fields (modifiedBy, modifiedDate)
     * 6. Save updated master record
     *
     * 7. Delete all old xref rows for this recipe (we follow fully replace strategy)
     * 8. Loop through xref DTO list:
     *      - Map DTO → RecipeIngredientXref entity
     *      - Assign recipe FK, ingredient FK
     *      - Assign audit fields
     *      - Save each xref row
     *
     * Notes:
     * - Update is done inside @Transactional → ensures atomicity
     * - No partial update will be saved if any error occurs
     * - Recipe code uniqueness is enforced
     *
     * @param recipeId ID of the recipe to modify
     * @param request Composite DTO containing master + xref updates
     * @return Updated RecipeMaster entity
     * @since 07-Nov-2025
     */
    @Override
    @Transactional
    public RecipeMaster updateRecipe(Integer recipeId, RecipeRequestDTO request) {

        // ----------------- FETCH MASTER -----------------
        RecipeMaster recipeMaster = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + recipeId));

        if (request == null || request.getRecipeMasterDTO() == null) {
            throw new IllegalArgumentException("Master data is missing");
        }

        RecipeMasterDTO recipeMasterDTO = request.getRecipeMasterDTO();

        if (recipeMasterDTO.getRecipeCode() == null || recipeMasterDTO.getRecipeCode().isBlank()) {
            throw new IllegalArgumentException("RecipeCode is required");
        }

        // Duplicate check (exclude current id)
        if (recipeRepo.existsByRecipeCodeIgnoreCaseAndRecipeIdNot(
                recipeMasterDTO.getRecipeCode(), recipeId)) {
            throw new DuplicateResourceException("Recipe code already exists: " + recipeMasterDTO.getRecipeCode());
        }

        // ----------------- MAP UPDATED FIELDS -----------------
        recipeMaster.setRecipeCode(recipeMasterDTO.getRecipeCode());
        recipeMaster.setRecipeName(recipeMasterDTO.getRecipeName());
        recipeMaster.setRecipeType(recipeMasterDTO.getRecipeType());
        recipeMaster.setDescription(recipeMasterDTO.getDescription());
        recipeMaster.setTotalCost(recipeMasterDTO.getTotalCost());
        recipeMaster.setSellingPrice(recipeMasterDTO.getSellingPrice());
        recipeMaster.setPreparationTime(recipeMasterDTO.getPreparationTime());
        recipeMaster.setActiveFlag(recipeMasterDTO.getActiveFlag());
        recipeMaster.setEnableFlag(recipeMasterDTO.getEnableFlag());
        recipeMaster.setModifiedDate(LocalDateTime.now());

        if (recipeMasterDTO.getModifiedById() != null) {
            recipeMaster.setModifiedBy(
                    userRepo.findById(recipeMasterDTO.getModifiedById())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeMasterDTO.getModifiedById()))
            );
        }

        RecipeMaster updated = recipeRepo.save(recipeMaster);

        // ----------------- DELETE OLD XREFS -----------------
        xrefRepo.deleteByRecipe_RecipeId(recipeId);

        // ----------------- SAVE NEW XREFS -----------------
        if (request.getRecipeIngredientXrefDTO() != null && !request.getRecipeIngredientXrefDTO().isEmpty()) {

            for (RecipeIngredientXrefDTO recipeIngredientXrefDTO : request.getRecipeIngredientXrefDTO()) {

                RecipeIngredientXref recipeIngredientXref = new RecipeIngredientXref();
                recipeIngredientXref.setRecipe(updated);

                recipeIngredientXref.setIngredient(
                        ingredientRepo.findById(recipeIngredientXrefDTO.getIngredientId())
                                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found: " + recipeIngredientXrefDTO.getIngredientId()))
                );

                recipeIngredientXref.setUom(recipeIngredientXrefDTO.getUom());
                recipeIngredientXref.setSmallerUnit(recipeIngredientXrefDTO.getSmallerUnit());
                recipeIngredientXref.setBaseUnitValue(recipeIngredientXrefDTO.getBaseUnitValue());
                recipeIngredientXref.setQuantityValue(recipeIngredientXrefDTO.getQuantityValue());
                recipeIngredientXref.setRemarks(recipeIngredientXrefDTO.getRemarks());
                recipeIngredientXref.setActiveFlag(recipeIngredientXrefDTO.getActiveFlag());
                recipeIngredientXref.setEnableFlag(recipeIngredientXrefDTO.getEnableFlag());
                recipeIngredientXref.setCreatedDate(recipeIngredientXrefDTO.getCreatedDate());
                recipeIngredientXref.setModifiedDate(LocalDateTime.now());

                if (recipeIngredientXrefDTO.getCreatedById() != null) {
                    recipeIngredientXref.setCreatedBy(
                            userRepo.findById(recipeIngredientXrefDTO.getCreatedById())
                                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeIngredientXrefDTO.getCreatedById()))
                    );
                }

                if (recipeIngredientXrefDTO.getModifiedById() != null) {
                    recipeIngredientXref.setModifiedBy(
                            userRepo.findById(recipeIngredientXrefDTO.getModifiedById())
                                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + recipeIngredientXrefDTO.getModifiedById()))
                    );
                }

                xrefRepo.save(recipeIngredientXref);
            }
        }

        return updated;
    }

    // -------------------- READ --------------------
//    @Override
//    @Transactional(readOnly = true)
//    public RecipeMasterDTO getRecipeById(Long recipeId) {
//        RecipeMaster entity = recipeRepo.findById(recipeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + recipeId));
//        return mapToDTO(entity);
//    }
    /**
     * Author: Ram Choudhary
     * Date: 07-Nov-2025
     *
     * Description:
     * This method fetches a single recipe along with all its ACTIVE ingredient xref rows.
     * It ensures:
     * 1. Recipe must be active and enabled (soft delete respected)
     * 2. Xrefs must also be active (inactive child rows are ignored)
     *
     * Process:
     * 1. Validate and fetch RecipeMaster by ID (only active + enabled)
     * 2. Convert RecipeMaster → RecipeMasterDTO
     * 3. Fetch all ACTIVE RecipeIngredientXref rows for the recipe
     * 4. Convert each xref → RecipeIngredientXrefDTO
     * 5. Return RecipeResponseDTO containing:
     *      - recipeMasterDTO
     *      - recipeMasterXrefs (list of ingredient xrefs)
     *
     * Notes:
     * - This method is read-only transactional for performance
     * - Soft-deleted records never appear in UI
     * - No NPE occurs if no xrefs exist
     *
     * @param recipeId ID of the recipe to fetch
     * @return RecipeResponseDTO containing master + xrefs
     * @since 07-Nov-2025
     */
    @Override
    @Transactional(readOnly = true)
    public RecipeResponseDTO getRecipeWithXrefs(Integer recipeId) {

        // Fetch recipe master
        RecipeMaster recipeMaster = recipeRepo
                .findByRecipeIdAndActiveFlagAndEnableFlag(recipeId, 1, 1)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found or inactive: " + recipeId));
        RecipeMasterDTO recipeMasterDTO = mapToDTO(recipeMaster);
        // Fetch only ACTIVE xrefs
        List<RecipeIngredientXref> recipeIngredientXrefList =
                xrefRepo.findByRecipe_RecipeIdAndActiveFlagAndEnableFlag(recipeId, 1, 1);

        // Convert xref list → DTO
        List<RecipeIngredientXrefDTO> xrefDTOList = new ArrayList<>();

        if (recipeIngredientXrefList != null && !recipeIngredientXrefList.isEmpty()) {
            for (RecipeIngredientXref recipeIngredientXref : recipeIngredientXrefList) {

                RecipeIngredientXrefDTO recipeIngredientXrefDTO = new RecipeIngredientXrefDTO();

                recipeIngredientXrefDTO.setRecipeXrefId(recipeIngredientXref.getRecipeXrefId());
                recipeIngredientXrefDTO.setIngredientId(recipeIngredientXref.getIngredient().getIngredientId());
                recipeIngredientXrefDTO.setUom(recipeIngredientXref.getUom());
                recipeIngredientXrefDTO.setSmallerUnit(recipeIngredientXref.getSmallerUnit());
                recipeIngredientXrefDTO.setBaseUnitValue(recipeIngredientXref.getBaseUnitValue());
                recipeIngredientXrefDTO.setQuantityValue(recipeIngredientXref.getQuantityValue());
                recipeIngredientXrefDTO.setRemarks(recipeIngredientXref.getRemarks());
                recipeIngredientXrefDTO.setActiveFlag(recipeIngredientXref.getActiveFlag());
                recipeIngredientXrefDTO.setEnableFlag(recipeIngredientXref.getEnableFlag());
                recipeIngredientXrefDTO.setCreatedDate(recipeIngredientXref.getCreatedDate());
                recipeIngredientXrefDTO.setModifiedDate(recipeIngredientXref.getModifiedDate());

                if (recipeIngredientXref.getCreatedBy() != null)
                    recipeIngredientXrefDTO.setCreatedById(recipeIngredientXref.getCreatedBy().getUserId());

                if (recipeIngredientXref.getModifiedBy() != null)
                    recipeIngredientXrefDTO.setModifiedById(recipeIngredientXref.getModifiedBy().getUserId());

                xrefDTOList.add(recipeIngredientXrefDTO);
            }
        }

        // Prepare response DTO
        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        recipeResponseDTO.setRecipeMaster(recipeMasterDTO);
        recipeResponseDTO.setRecipeMasterXrefs(xrefDTOList);

        return recipeResponseDTO;
    }

    //    @Override
//    @Transactional(readOnly = true)
//    public List<RecipeMasterDTO> getAllRecipes() {
//        return recipeRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
//    }
    @Override
    @Transactional(readOnly = true)
    public List<RecipeMasterDTO> getAllRecipes() {

        // Fetch only active recipes
        List<RecipeMaster> recipeMasterList =
                recipeRepo.findByActiveFlagAndEnableFlag(1, 1);

        List<RecipeMasterDTO> recipeMasterDTOList = new ArrayList<>();

        if (recipeMasterList != null && !recipeMasterList.isEmpty()) {
            for (RecipeMaster recipeMaster : recipeMasterList) {
                recipeMasterDTOList.add(mapToDTO(recipeMaster));
            }
        }

        return recipeMasterDTOList;
    }

    // -------------------- DELETE --------------------
//    @Override
//    @Transactional
//    public void deleteRecipe(Long recipeId) {
//        RecipeMaster entity = recipeRepo.findById(recipeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + recipeId));
//        xrefRepo.deleteByRecipe_RecipeId(recipeId);
//        recipeRepo.delete(entity);
//    }
    @Override
    @Transactional
    public void deleteRecipe(Integer recipeId) {

        // Fetch recipe master
        RecipeMaster recipeMaster = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found: " + recipeId));

        // ---- Soft delete master ----
        recipeMaster.setActiveFlag(0);
        recipeMaster.setEnableFlag(0);
        recipeMaster.setModifiedDate(LocalDateTime.now());

        recipeRepo.save(recipeMaster);

        // ---- Soft delete all xrefs ----
        List<RecipeIngredientXref> recipeIngredientXrefList =
                xrefRepo.findByRecipe_RecipeId(recipeId);

        // If no xrefs found → simply skip
        if (recipeIngredientXrefList != null && !recipeIngredientXrefList.isEmpty()) {
            for (RecipeIngredientXref recipeIngredientXref : recipeIngredientXrefList) {
                recipeIngredientXref.setActiveFlag(0);
                recipeIngredientXref.setEnableFlag(0);
                recipeIngredientXref.setModifiedDate(LocalDateTime.now());
                xrefRepo.save(recipeIngredientXref);
            }
        }
    }

    private RecipeMasterDTO mapToDTO(RecipeMaster e) {
        RecipeMasterDTO dto = new RecipeMasterDTO();
        dto.setRecipeId(e.getRecipeId());
        dto.setRecipeCode(e.getRecipeCode());
        dto.setRecipeName(e.getRecipeName());
        dto.setRecipeType(e.getRecipeType());
        dto.setDescription(e.getDescription());
        dto.setTotalCost(e.getTotalCost());
        dto.setSellingPrice(e.getSellingPrice());
        dto.setPreparationTime(e.getPreparationTime());
        dto.setActiveFlag(e.getActiveFlag());
        dto.setEnableFlag(e.getEnableFlag());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setModifiedDate(e.getModifiedDate());
        dto.setCreatedById(e.getCreatedBy() != null ? e.getCreatedBy().getUserId() : null);
        dto.setModifiedById(e.getModifiedBy() != null ? e.getModifiedBy().getUserId() : null);
        return dto;
    }

    // -------------------- Mapping Helpers --------------------

    // If you add pricing on IngredientsMaster, implement:
    // private BigDecimal calculateTotalCost(Long recipeId) { ... }
}
