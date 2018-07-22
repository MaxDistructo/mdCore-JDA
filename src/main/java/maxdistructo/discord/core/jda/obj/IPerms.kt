package maxdistructo.discord.core.jda.obj

/**
 * @interface IPerms
 * @description Simple Permissions System. No default implementation due to how varied your approach can be.
 * @author MaxDistructo
 */

interface IPerms {
    var admins : List<Long>
    var mods : List<Long>
    fun addAdmin(l : Long){
        admins += l
        mods += l
    }
    fun addMod(l : Long){
        mods += l
    }
    fun checkAdmin(l : Long) : Boolean{
        return admins.contains(l)
    }
    fun checkMod(l : Long) : Boolean{
        return mods.contains(l)
    }
}

