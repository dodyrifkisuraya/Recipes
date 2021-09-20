package com.dorizu.masako.saved

import android.content.Context
import com.dorizu.masako.di.DynamicFeatureDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [DynamicFeatureDependencies::class])
interface SavedComponent {

    fun inject(activity: SavedRecipesActivity)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(dependencies: DynamicFeatureDependencies): Builder
        fun build(): SavedComponent
    }
}