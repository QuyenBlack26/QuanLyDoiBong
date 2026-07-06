package com.example.quanli.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.quanli.model.Match
import com.example.quanli.model.Player
import com.example.quanli.model.Standing
import com.example.quanli.model.UserAccount

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "QuanLyBongDa.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_QUOC_GIA = "QuocGia"
        const val TABLE_CAU_LAC_BO = "CauLacBo"
        const val TABLE_GIAI_DAU = "GiaiDau"
        const val TABLE_CAU_THU = "CauThu"
        const val TABLE_HOP_DONG = "HopDong"
        const val TABLE_TRAN_DAU = "TranDau"
        const val TABLE_SU_KIEN_TRAN = "SuKienTran"
        const val TABLE_BANG_XEP_HANG = "BangXepHang"
        const val TABLE_TAI_KHOAN = "TAIKHOAN"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables based on SQL-UNGDUNG.sql
        db.execSQL("CREATE TABLE $TABLE_QUOC_GIA (MaQG CHAR(3) PRIMARY KEY, TenQG TEXT NOT NULL)")
        
        db.execSQL("CREATE TABLE $TABLE_CAU_LAC_BO (MaCLB TEXT PRIMARY KEY, TenCLB TEXT NOT NULL, MaQG CHAR(3), NamThanhLap INTEGER, SanNha TEXT, HuanLuyenVien TEXT, FOREIGN KEY (MaQG) REFERENCES $TABLE_QUOC_GIA(MaQG))")
        
        db.execSQL("CREATE TABLE $TABLE_GIAI_DAU (MaGiai TEXT PRIMARY KEY, TenGiai TEXT NOT NULL, MaQG CHAR(3), MuaGiai CHAR(9) NOT NULL, FOREIGN KEY (MaQG) REFERENCES $TABLE_QUOC_GIA(MaQG))")
        
        db.execSQL("CREATE TABLE $TABLE_CAU_THU (MaCT TEXT PRIMARY KEY, HoTen TEXT NOT NULL, NgaySinh TEXT, MaQG CHAR(3), ViTri TEXT, ChieuCao INTEGER, CanNang INTEGER, FOREIGN KEY (MaQG) REFERENCES $TABLE_QUOC_GIA(MaQG))")
        
        db.execSQL("CREATE TABLE $TABLE_HOP_DONG (MaHD TEXT PRIMARY KEY, MaCT TEXT NOT NULL, SoAo INTEGER, NgayBatDau TEXT, NgayKetThuc TEXT, FOREIGN KEY (MaCT) REFERENCES $TABLE_CAU_THU(MaCT))")
        
        db.execSQL("CREATE TABLE $TABLE_TRAN_DAU (MaTran TEXT PRIMARY KEY, MaGiai TEXT NOT NULL, DoiNha TEXT NOT NULL, DoiKhach TEXT NOT NULL, NgayThi TEXT, SanDau TEXT, BanThangNha INTEGER DEFAULT 0, BanThangKhach INTEGER DEFAULT 0, TrangThai TEXT DEFAULT 'Chưa đấu', FOREIGN KEY (MaGiai) REFERENCES $TABLE_GIAI_DAU(MaGiai))")
        
        db.execSQL("CREATE TABLE $TABLE_SU_KIEN_TRAN (MaSuKien INTEGER PRIMARY KEY AUTOINCREMENT, MaTran TEXT NOT NULL, MaCT TEXT NOT NULL, LoaiSuKien TEXT NOT NULL, Phut INTEGER, GhiChu TEXT, FOREIGN KEY (MaTran) REFERENCES $TABLE_TRAN_DAU(MaTran), FOREIGN KEY (MaCT) REFERENCES $TABLE_CAU_THU(MaCT))")
        
        db.execSQL("CREATE TABLE $TABLE_BANG_XEP_HANG (MaGiai TEXT PRIMARY KEY, SoTran INTEGER DEFAULT 0, Thang INTEGER DEFAULT 0, Hoa INTEGER DEFAULT 0, Thua INTEGER DEFAULT 0, BanThang INTEGER DEFAULT 0, BanThua INTEGER DEFAULT 0, Diem INTEGER DEFAULT 0, FOREIGN KEY (MaGiai) REFERENCES $TABLE_GIAI_DAU(MaGiai))")
        
        db.execSQL("CREATE TABLE $TABLE_TAI_KHOAN (MaTK INTEGER PRIMARY KEY AUTOINCREMENT, TenDangNhap TEXT NOT NULL UNIQUE, MatKhau TEXT NOT NULL, Email TEXT UNIQUE, HoTen TEXT, VaiTro TEXT NOT NULL DEFAULT 'User')")

        insertSampleData(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        // DỮ LIỆU MẪU khớp hoàn toàn với hình ảnh MySQL Workbench của bạn
        
        // Quốc gia
        db.execSQL("INSERT INTO $TABLE_QUOC_GIA VALUES ('VIE', 'Việt Nam')")
        db.execSQL("INSERT INTO $TABLE_QUOC_GIA VALUES ('BRA', 'Brazil')")
        db.execSQL("INSERT INTO $TABLE_QUOC_GIA VALUES ('KOR', 'Hàn Quốc')")

        // Cầu thủ (Khớp chính xác với Result Grid trong ảnh của bạn)
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT000', 'HUY ANH DUNG', '1996-01-10', 'VIE', 'Tiền đạo', 175, 72)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT001', 'toro', '1993-06-13', 'BRA', 'Thủ môn', 187, 82)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT002', 'Quế Ngọc Hải', '1993-12-20', 'VIE', 'Hậu vệ', 178, 74)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT003', 'Nguyễn Quang Hải', '1997-04-12', 'VIE', 'Tiền vệ', 168, 60)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT004', 'Tiến Linh', '1997-03-25', 'VIE', 'Tiền đạo', 180, 74)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT005', 'LUBU', '1996-01-10', 'KOR', 'Tiền vệ', 175, 72)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT006', 'RÔ Ra Nguyên', '1996-01-10', 'VIE', 'Tiền vệ', 175, 72)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT007', 'ĐĂNG ME SY', '1996-01-10', 'VIE', 'Hậu vệ', 175, 72)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT008', 'Tâm', '1996-01-10', 'VIE', 'Hậu vệ', 175, 72)")
        db.execSQL("INSERT INTO $TABLE_CAU_THU VALUES ('CT009', 'Gia Huy', '1996-01-10', 'BRA', 'Hậu vệ', 175, 72)")

        // Tài khoản
        db.execSQL("INSERT INTO $TABLE_TAI_KHOAN (TenDangNhap, MatKhau, Email, HoTen, VaiTro) VALUES ('admin', '123456', 'admin@hanoifc.vn', 'Quản trị viên', 'Admin')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAI_KHOAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BANG_XEP_HANG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SU_KIEN_TRAN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRAN_DAU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HOP_DONG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CAU_THU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GIAI_DAU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CAU_LAC_BO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUOC_GIA")
        onCreate(db)
    }

    fun getAllPlayers(): List<Player> {
        val playerList = mutableListOf<Player>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CAU_THU", null)

        if (cursor.moveToFirst()) {
            do {
                val player = Player(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("MaCT")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("HoTen")),
                    birthDate = cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")),
                    nationality = cursor.getString(cursor.getColumnIndexOrThrow("MaQG")),
                    position = cursor.getString(cursor.getColumnIndexOrThrow("ViTri")),
                    height = cursor.getInt(cursor.getColumnIndexOrThrow("ChieuCao")),
                    weight = cursor.getInt(cursor.getColumnIndexOrThrow("CanNang"))
                )
                playerList.add(player)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return playerList
    }

    fun getAllMatches(): List<Match> {
        val matchList = mutableListOf<Match>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TRAN_DAU", null)

        if (cursor.moveToFirst()) {
            do {
                matchList.add(Match(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("MaTran")),
                    tournamentId = cursor.getString(cursor.getColumnIndexOrThrow("MaGiai")),
                    homeTeam = cursor.getString(cursor.getColumnIndexOrThrow("DoiNha")),
                    awayTeam = cursor.getString(cursor.getColumnIndexOrThrow("DoiKhach")),
                    matchDate = cursor.getString(cursor.getColumnIndexOrThrow("NgayThi")),
                    stadium = cursor.getString(cursor.getColumnIndexOrThrow("SanDau")),
                    homeScore = cursor.getInt(cursor.getColumnIndexOrThrow("BanThangNha")),
                    awayScore = cursor.getInt(cursor.getColumnIndexOrThrow("BanThangKhach")),
                    status = cursor.getString(cursor.getColumnIndexOrThrow("TrangThai"))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return matchList
    }

    fun getAllStandings(): List<Standing> {
        val standingList = mutableListOf<Standing>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BANG_XEP_HANG", null)

        if (cursor.moveToFirst()) {
            do {
                standingList.add(Standing(
                    tournamentId = cursor.getString(cursor.getColumnIndexOrThrow("MaGiai")),
                    played = cursor.getInt(cursor.getColumnIndexOrThrow("SoTran")),
                    won = cursor.getInt(cursor.getColumnIndexOrThrow("Thang")),
                    drawn = cursor.getInt(cursor.getColumnIndexOrThrow("Hoa")),
                    lost = cursor.getInt(cursor.getColumnIndexOrThrow("Thua")),
                    goalsFor = cursor.getInt(cursor.getColumnIndexOrThrow("BanThang")),
                    goalsAgainst = cursor.getInt(cursor.getColumnIndexOrThrow("BanThua")),
                    points = cursor.getInt(cursor.getColumnIndexOrThrow("Diem"))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return standingList
    }

    fun getUserByUsername(username: String): UserAccount? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TAI_KHOAN WHERE TenDangNhap = ?", arrayOf(username))
        var user: UserAccount? = null
        if (cursor.moveToFirst()) {
            user = UserAccount(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("MaTK")),
                username = cursor.getString(cursor.getColumnIndexOrThrow("TenDangNhap")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("Email")),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow("HoTen")),
                role = cursor.getString(cursor.getColumnIndexOrThrow("VaiTro"))
            )
        }
        cursor.close()
        return user
    }

    fun checkLogin(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_TAI_KHOAN WHERE TenDangNhap = ? AND MatKhau = ?",
            arrayOf(username, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun registerUser(username: String, password: String, email: String, fullName: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("TenDangNhap", username)
            put("MatKhau", password)
            put("Email", email)
            put("HoTen", fullName)
            put("VaiTro", "User")
        }
        return db.insert(TABLE_TAI_KHOAN, null, values)
    }
}
