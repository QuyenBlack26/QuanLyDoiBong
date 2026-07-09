package com.example.quanli.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quanli.R
import com.example.quanli.adapter.PlayerAdapter
import com.example.quanli.databinding.ActivityPlayerManagementBinding
import com.example.quanli.databinding.DialogPlayerBinding
import com.example.quanli.model.PlayerModel

/**
 * PlayerManagementActivity handles the UI for managing the football club's players.
 * Improved with Search, Filter, and actual List management.
 */
class PlayerManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerManagementBinding
    private lateinit var playerAdapter: PlayerAdapter
    
    // originalList stores all players (database representation)
    private val originalList = mutableListOf<PlayerModel>()
    // displayList stores players currently shown (filtered/searched)
    private val displayList = mutableListOf<PlayerModel>()

    // Shared dropdown data to maintain consistency and reduce duplication
    private val countries = arrayOf("Việt Nam", "Thái Lan", "Brazil", "Anh", "Pháp", "Đức", "Tây Ban Nha", "Argentina", "Bồ Đào Nha")
    private val positions = arrayOf("Thủ môn", "Hậu vệ", "Tiền vệ", "Tiền đạo")
    private val clubs = arrayOf("Hà Nội FC", "HAGL", "Viettel", "Manchester United", "Real Madrid", "Barcelona")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupDummyData()
        setupRecyclerView()
        setupFilters()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player_management, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                binding.etSearch.requestFocus()
                true
            }
            R.id.action_more -> {
                Toast.makeText(this, getString(R.string.msg_more_options), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        displayList.addAll(originalList)
        playerAdapter = PlayerAdapter(
            players = displayList,
            onEditClick = { player -> showPlayerDialog(player) },
            onDeleteClick = { player -> confirmDeletePlayer(player) },
            onItemClick = { player ->
                Toast.makeText(this, getString(R.string.msg_details, player.name), Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvPlayers.adapter = playerAdapter
        checkEmptyState()
    }

    private fun setupFilters() {
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        binding.autoCompleteNationality.setAdapter(countryAdapter)

        val positionAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, positions)
        binding.autoCompletePosition.setAdapter(positionAdapter)

        val clubAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, clubs)
        binding.autoCompleteClub.setAdapter(clubAdapter)
    }

    private fun setupListeners() {
        binding.fabAddPlayer.setOnClickListener {
            showPlayerDialog(null)
        }

        binding.btnFilter.setOnClickListener {
            applyFilters()
        }

        // Real-time Search Implementation
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters() // Apply both search and filters instantly
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * Filters the original list based on search text and selected dropdown values.
     */
    private fun applyFilters() {
        val searchText = binding.etSearch.text.toString().lowercase()
        val selectedCountry = binding.autoCompleteNationality.text.toString()
        val selectedPosition = binding.autoCompletePosition.text.toString()
        val selectedClub = binding.autoCompleteClub.text.toString()

        val filtered = originalList.filter { player ->
            val matchesSearch = player.name.lowercase().contains(searchText)
            val matchesCountry = selectedCountry.isEmpty() || player.nationality == selectedCountry
            val matchesPosition = selectedPosition.isEmpty() || player.position == selectedPosition
            val matchesClub = selectedClub.isEmpty() || player.club == selectedClub

            matchesSearch && matchesCountry && matchesPosition && matchesClub
        }

        displayList.clear()
        displayList.addAll(filtered)
        playerAdapter.updateData(displayList.toList())
        checkEmptyState()
    }

    /**
     * Shows a dialog to add a new player or edit an existing one.
     * Includes field validation and list updates.
     */
    private fun showPlayerDialog(player: PlayerModel?) {
        val dialogBinding = DialogPlayerBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        
        val alertDialog = builder.create()

        // Setup dropdowns in dialog
        dialogBinding.autoCompleteDialogNationality.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries))
        dialogBinding.autoCompleteDialogPosition.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, positions))
        dialogBinding.autoCompleteDialogClub.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, clubs))

        if (player != null) {
            dialogBinding.tvDialogTitle.text = getString(R.string.lbl_edit_player)
            dialogBinding.etPlayerId.setText(player.playerId)
            dialogBinding.etPlayerId.isEnabled = false // Database ID usually immutable
            dialogBinding.etFullName.setText(player.name)
            dialogBinding.etBirthDate.setText(player.birthDate)
            dialogBinding.autoCompleteDialogNationality.setText(player.nationality, false)
            dialogBinding.autoCompleteDialogPosition.setText(player.position, false)
            dialogBinding.etHeight.setText(player.height.toString())
            dialogBinding.etWeight.setText(player.weight.toString())
            dialogBinding.autoCompleteDialogClub.setText(player.club, false)
            dialogBinding.etJerseyNumber.setText(player.jerseyNumber.toString())
        }

        dialogBinding.btnCancel.setOnClickListener { alertDialog.dismiss() }
        dialogBinding.btnSave.setOnClickListener {
            if (validateFields(dialogBinding, player != null)) {
                val newPlayer = PlayerModel(
                    playerId = dialogBinding.etPlayerId.text.toString(),
                    name = dialogBinding.etFullName.text.toString(),
                    birthDate = dialogBinding.etBirthDate.text.toString(),
                    nationality = dialogBinding.autoCompleteDialogNationality.text.toString(),
                    position = dialogBinding.autoCompleteDialogPosition.text.toString(),
                    height = dialogBinding.etHeight.text.toString().toIntOrNull() ?: 0,
                    weight = dialogBinding.etWeight.text.toString().toIntOrNull() ?: 0,
                    club = dialogBinding.autoCompleteDialogClub.text.toString(),
                    jerseyNumber = dialogBinding.etJerseyNumber.text.toString().toIntOrNull() ?: 0,
                    avatar = player?.avatar ?: R.drawable.ic_player_placeholder
                )

                if (player == null) {
                    // Create new player entry
                    originalList.add(newPlayer)
                    Toast.makeText(this, getString(R.string.msg_player_added), Toast.LENGTH_SHORT).show()
                } else {
                    // Update existing player entry
                    val index = originalList.indexOfFirst { it.playerId == player.playerId }
                    if (index != -1) {
                        originalList[index] = newPlayer
                        Toast.makeText(this, getString(R.string.msg_player_updated), Toast.LENGTH_SHORT).show()
                    }
                }
                
                applyFilters() // Refresh UI immediately
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    /**
     * Comprehensive field validation for the player dialog.
     */
    private fun validateFields(dialogBinding: DialogPlayerBinding, isEdit: Boolean): Boolean {
        var isValid = true

        // 1. Player ID Validation
        val enteredId = dialogBinding.etPlayerId.text.toString().trim()
        if (enteredId.isBlank()) {
            dialogBinding.etPlayerId.error = getString(R.string.error_id_required)
            isValid = false
        } else if (!isEdit && originalList.any { it.playerId == enteredId }) {
            dialogBinding.etPlayerId.error = getString(R.string.error_id_exists)
            isValid = false
        }

        // 2. Full Name Validation
        if (dialogBinding.etFullName.text.isNullOrBlank()) {
            dialogBinding.etFullName.error = getString(R.string.error_name_required)
            isValid = false
        }

        // 3. Birth Date Validation (dd/MM/yyyy)
        val birthDate = dialogBinding.etBirthDate.text.toString().trim()
        val dateRegex = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/\\d{4}$".toRegex()
        if (birthDate.isBlank()) {
            dialogBinding.etBirthDate.error = getString(R.string.error_date_required)
            isValid = false
        } else if (!dateRegex.matches(birthDate)) {
            dialogBinding.etBirthDate.error = getString(R.string.error_date_format)
            isValid = false
        }

        // 4. Height Validation (100 - 250 cm)
        val heightStr = dialogBinding.etHeight.text.toString().trim()
        val height = heightStr.toIntOrNull()
        if (heightStr.isBlank()) {
            dialogBinding.etHeight.error = getString(R.string.error_height_required)
            isValid = false
        } else if (height == null || height !in 100..250) {
            dialogBinding.etHeight.error = getString(R.string.error_height_invalid)
            isValid = false
        }

        // 5. Weight Validation (30 - 200 kg)
        val weightStr = dialogBinding.etWeight.text.toString().trim()
        val weight = weightStr.toIntOrNull()
        if (weightStr.isBlank()) {
            dialogBinding.etWeight.error = getString(R.string.error_weight_required)
            isValid = false
        } else if (weight == null || weight !in 30..200) {
            dialogBinding.etWeight.error = getString(R.string.error_weight_invalid)
            isValid = false
        }

        // 6. Jersey Number Validation (1 - 99)
        val jerseyStr = dialogBinding.etJerseyNumber.text.toString().trim()
        val jersey = jerseyStr.toIntOrNull()
        if (jerseyStr.isBlank()) {
            dialogBinding.etJerseyNumber.error = getString(R.string.error_jersey_required)
            isValid = false
        } else if (jersey == null || jersey !in 1..99) {
            dialogBinding.etJerseyNumber.error = getString(R.string.error_jersey_invalid)
            isValid = false
        }

        return isValid
    }

    /**
     * Confirms and deletes a player from the list.
     */
    private fun confirmDeletePlayer(player: PlayerModel) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_confirm_delete))
            .setMessage(getString(R.string.msg_confirm_delete, player.name))
            .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                originalList.removeAll { it.playerId == player.playerId }
                applyFilters()
                Toast.makeText(this, getString(R.string.msg_player_deleted, player.name), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .show()
    }

    /**
     * Handles visibility of the empty state message.
     */
    private fun checkEmptyState() {
        if (displayList.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.rvPlayers.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.rvPlayers.visibility = View.VISIBLE
        }
    }

    /**
     * Generates dummy data with database-like IDs (CTxxx).
     */
    private fun setupDummyData() {
        originalList.add(PlayerModel("CT001", "Nguyễn Quang Hải", "12/04/1997", "Việt Nam", "Tiền vệ", 168, 65, "Hà Nội FC", 19, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT002", "Nguyễn Công Phượng", "21/01/1995", "Việt Nam", "Tiền đạo", 168, 65, "HAGL", 10, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT003", "Đặng Văn Lâm", "13/08/1993", "Việt Nam", "Thủ môn", 188, 85, "Viettel", 1, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT004", "Đỗ Duy Mạnh", "29/09/1996", "Việt Nam", "Hậu vệ", 180, 75, "Hà Nội FC", 2, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT005", "Lionel Messi", "24/06/1987", "Argentina", "Tiền đạo", 170, 72, "Manchester United", 10, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT006", "Cristiano Ronaldo", "05/02/1985", "Bồ Đào Nha", "Tiền đạo", 187, 83, "Real Madrid", 7, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT007", "Kylian Mbappé", "20/12/1998", "Pháp", "Tiền đạo", 178, 73, "Real Madrid", 9, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT008", "Đoàn Văn Hậu", "19/04/1999", "Việt Nam", "Hậu vệ", 185, 80, "Hà Nội FC", 5, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT009", "Nguyễn Tuấn Anh", "16/05/1995", "Việt Nam", "Tiền vệ", 176, 70, "HAGL", 11, R.drawable.ic_player_placeholder))
        originalList.add(PlayerModel("CT010", "Đỗ Hùng Dũng", "08/09/1993", "Việt Nam", "Tiền vệ", 170, 68, "Hà Nội FC", 8, R.drawable.ic_player_placeholder))
    }
}
