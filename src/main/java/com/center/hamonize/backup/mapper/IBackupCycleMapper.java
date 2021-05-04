package com.center.hamonize.backup.mapper;

import com.center.hamonize.backup.BackupCycleVo;

public interface IBackupCycleMapper {
	public BackupCycleVo backupCycleList(BackupCycleVo vo);

	public void deleteBackupCycle(BackupCycleVo vo);
	
	public void backupCycleInsert(BackupCycleVo vo);
}