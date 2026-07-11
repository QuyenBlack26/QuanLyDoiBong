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
import com.google.android.material.snackbar.Snackbar

/**
 * PlayerManagementActivity handles the UI for managing the football club's players.
 * Features: Real-time search, multi-criteria filtering, sorting, and CRUD operations.
 */
class PlayerManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerManagementBinding
    private lateinit var playerAdapter: PlayerAdapter
    
    private val originalList = mutableListOf<PlayerModel>()
    private val displayList = mutableListOf<PlayerModel>()

    private val countries = arrayOf("Việt Nam", "Thái Lan", "Brazil", "Anh", "Pháp", "Đức", "Tây Ban Nha", "Argentina", "Bồ Đào Nha")
    private val positions = arrayOf("Thủ môn", "Hậu vệ", "Tiền vệ", "Tiền đạo")
    private val clubs = arrayOf("Hà Nội FC", "HAGL", "Viettel", "Manchester United", "Real Madrid", "Barcelona", "Al Nassr")

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
            R.id.action_sort_name -> {
                sortPlayers { it.name }
                true
            }
            R.id.action_sort_height -> {
                sortPlayers { it.height }
                true
            }
            R.id.action_sort_number -> {
                sortPlayers { it.jerseyNumber }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun <T : Comparable<T>> sortPlayers(selector: (PlayerModel) -> T) {
        val sortedList = displayList.sortedBy { selector(it) }
        displayList.clear()
        displayList.addAll(sortedList)
        playerAdapter.updateData(displayList.toList())
    }

    private fun setupRecyclerView() {
        displayList.addAll(originalList)
        playerAdapter = PlayerAdapter(
            players = displayList,
            onEditClick = { player -> showPlayerDialog(player) },
            onDeleteClick = { player -> confirmDeletePlayer(player) },
            onItemClick = { player ->
                showSnackbar(getString(R.string.msg_details, player.name))
            }
        )
        binding.rvPlayers.adapter = playerAdapter
        checkEmptyState()
    }

    private fun setupFilters() {
        val adapter = { list: Array<String> -> ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list) }
        binding.autoCompleteNationality.setAdapter(adapter(countries))
        binding.autoCompletePosition.setAdapter(adapter(positions))
        binding.autoCompleteClub.setAdapter(adapter(clubs))
    }

    private fun setupListeners() {
        binding.fabAddPlayer.setOnClickListener {
            showPlayerDialog(null)
        }

        binding.btnFilter.setOnClickListener {
            applyFilters()
        }

        binding.btnReset.setOnClickListener {
            binding.etSearch.text?.clear()
            binding.autoCompleteNationality.setText("", false)
            binding.autoCompletePosition.setText("", false)
            binding.autoCompleteClub.setText("", false)
            applyFilters()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun applyFilters() {
        val query = binding.etSearch.text.toString().trim().lowercase()
        val country = binding.autoCompleteNationality.text.toString()
        val position = binding.autoCompletePosition.text.toString()
        val club = binding.autoCompleteClub.text.toString()

        val filtered = originalList.filter {
            (it.name.lowercase().contains(query) || it.playerId.lowercase().contains(query)) &&
            (country.isEmpty() || it.nationality == country) &&
            (position.isEmpty() || it.position == position) &&
            (club.isEmpty() || it.club == club)
        }

        displayList.clear()
        displayList.addAll(filtered)
        playerAdapter.updateData(displayList.toList())
        checkEmptyState()
    }

    private fun showPlayerDialog(player: PlayerModel?) {
        val dialogBinding = DialogPlayerBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        
        val alertDialog = builder.create()

        val adapter = { list: Array<String> -> ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list) }
        dialogBinding.autoCompleteDialogNationality.setAdapter(adapter(countries))
        dialogBinding.autoCompleteDialogPosition.setAdapter(adapter(positions))
        dialogBinding.autoCompleteDialogClub.setAdapter(adapter(clubs))

        if (player != null) {
            dialogBinding.tvDialogTitle.text = getString(R.string.lbl_edit_player)
            dialogBinding.etPlayerId.setText(player.playerId)
            dialogBinding.etPlayerId.isEnabled = false
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
                    playerId = dialogBinding.etPlayerId.text.toString().trim(),
                    name = dialogBinding.etFullName.text.toString().trim(),
                    birthDate = dialogBinding.etBirthDate.text.toString().trim(),
                    nationality = dialogBinding.autoCompleteDialogNationality.text.toString(),
                    position = dialogBinding.autoCompleteDialogPosition.text.toString(),
                    height = dialogBinding.etHeight.text.toString().toInt(),
                    weight = dialogBinding.etWeight.text.toString().toInt(),
                    club = dialogBinding.autoCompleteDialogClub.text.toString(),
                    jerseyNumber = dialogBinding.etJerseyNumber.text.toString().toInt(),
                    avatar = player?.avatar ?: R.drawable.ic_player_placeholder
                )

                if (player == null) {
                    originalList.add(newPlayer)
                    showSnackbar(getString(R.string.msg_player_added, newPlayer.name))
                } else {
                    val index = originalList.indexOfFirst { it.playerId == player.playerId }
                    if (index != -1) {
                        originalList[index] = newPlayer
                        showSnackbar(getString(R.string.msg_player_updated, newPlayer.name))
                    }
                }
                
                applyFilters()
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    private fun validateFields(db: DialogPlayerBinding, isEdit: Boolean): Boolean {
        var valid = true
        val id = db.etPlayerId.text.toString().trim()
        if (id.isEmpty()) { 
            db.etPlayerId.error = getString(R.string.error_id_required)
            db.etPlayerId.requestFocus()
            valid = false 
        } else if (!isEdit && originalList.any { it.playerId == id }) { 
            db.etPlayerId.error = getString(R.string.error_id_exists)
            db.etPlayerId.requestFocus()
            valid = false 
        }
        
        if (db.etFullName.text.isNullOrBlank()) { 
            db.etFullName.error = getString(R.string.error_name_required)
            if(valid) db.etFullName.requestFocus()
            valid = false 
        }
        
        val dateRegex = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/\\d{4}$".toRegex()
        if (!dateRegex.matches(db.etBirthDate.text.toString().trim())) { 
            db.etBirthDate.error = getString(R.string.error_date_format)
            if(valid) db.etBirthDate.requestFocus()
            valid = false 
        }
        
        val h = db.etHeight.text.toString().toIntOrNull()
        if (h == null || h !in 100..250) { 
            db.etHeight.error = getString(R.string.error_height_invalid)
            if(valid) db.etHeight.requestFocus()
            valid = false 
        }
        
        val w = db.etWeight.text.toString().toIntOrNull()
        if (w == null || w !in 30..200) { 
            db.etWeight.error = getString(R.string.error_weight_invalid)
            if(valid) db.etWeight.requestFocus()
            valid = false 
        }
        
        val j = db.etJerseyNumber.text.toString().toIntOrNull()
        if (j == null || j !in 1..99) { 
            db.etJerseyNumber.error = getString(R.string.error_jersey_invalid)
            if(valid) db.etJerseyNumber.requestFocus()
            valid = false 
        }
        
        return valid
    }

    private fun confirmDeletePlayer(player: PlayerModel) {
        val deletedPlayer = player
        val deletedIndex = originalList.indexOf(player)
        
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_confirm_delete))
            .setMessage(getString(R.string.msg_confirm_delete, player.name))
            .setPositiveButton(getString(R.string.btn_delete)) { _, _ ->
                originalList.remove(player)
                applyFilters()
                
                Snackbar.make(binding.root, getString(R.string.msg_player_deleted, player.name), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.msg_undo)) {
                        originalList.add(deletedIndex, deletedPlayer)
                        applyFilters()
                    }.show()
            }
            .setNegativeButton(getString(R.string.btn_cancel), null).show()
    }

    private fun showSnackbar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    private fun checkEmptyState() {
        binding.tvEmptyState.visibility = if (displayList.isEmpty()) View.VISIBLE else View.GONE
        binding.rvPlayers.visibility = if (displayList.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun setupDummyData() {
        originalList.addAll(listOf(
            PlayerModel("CT001", "Nguyễn Quang Hải", "12/04/1997", "Việt Nam", "Tiền vệ", 168, 65, "Hà Nội FC", 19, R.drawable.ic_player_placeholder),
            PlayerModel("CT002", "Nguyễn Công Phượng", "21/01/1995", "Việt Nam", "Tiền đạo", 168, 65, "HAGL", 10, R.drawable.ic_player_placeholder),
            PlayerModel("CT003", "Đặng Văn Lâm", "13/08/1993", "Việt Nam", "Thủ môn", 188, 85, "Viettel", 1, R.drawable.ic_player_placeholder),
            PlayerModel("CT004", "Đỗ Duy Mạnh", "29/09/1996", "Việt Nam", "Hậu vệ", 180, 75, "Hà Nội FC", 2, R.drawable.ic_player_placeholder),
            PlayerModel("CT005", "Lionel Messi", "24/06/1987", "Argentina", "Tiền đạo", 170, 72, "Barcelona", 10, R.drawable.ic_player_placeholder),
            PlayerModel("CT006", "Cristiano Ronaldo", "05/02/1985", "Bồ Đào Nha", "Tiền đạo", 187, 83, "Al Nassr", 7, R.drawable.ic_player_placeholder),
            PlayerModel("CT007", "Kylian Mbappé", "20/12/1998", "Pháp", "Tiền đạo", 178, 73, "Real Madrid", 9, R.drawable.ic_player_placeholder),
            PlayerModel("CT008", "Đoàn Văn Hậu", "19/04/1999", "Việt Nam", "Hậu vệ", 185, 80, "Hà Nội FC", 5, R.drawable.ic_player_placeholder),
            PlayerModel("CT009", "Nguyễn Tuấn Anh", "16/05/1995", "Việt Nam", "Tiền vệ", 176, 70, "HAGL", 11, R.drawable.ic_player_placeholder),
            PlayerModel("CT010", "Đỗ Hùng Dũng", "08/09/1993", "Việt Nam", "Tiền vệ", 170, 68, "Hà Nội FC", 8, R.drawable.ic_player_placeholder)
        ))
    }
}
