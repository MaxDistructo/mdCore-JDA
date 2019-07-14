package maxdistructo.discord.core.jda.obj

/**
 * @interface IPerms
 * @description Simple Permissions System. No default implementation due to how varied your approach can be.
 * @author MaxDistructo
 */

interface IPerms {
    var admins : MutableList<Long>
    var mods : MutableList<Long>
    fun addAdmin(l : Long){
        admins.add(l)
        mods.add(l)
    }
    fun addMod(l : Long){
        mods.add(l)
    }
    fun checkAdmin(l : Long) : Boolean{
        return admins.contains(l)
    }
    fun checkMod(l : Long) : Boolean{
        return mods.contains(l)
    }
}

