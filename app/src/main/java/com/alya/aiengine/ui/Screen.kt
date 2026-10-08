package com.alya.aiengine.ui

/**
 * The full navigation flow for Alya AI Engine (stage 1):
 *
 * INTRO (intro_video, muted, plays once)
 *   -> HOME (background_video, muted, looping)
 *      -> press PLAY -> PLAY_INTRO (play_intro_video, audio ON, plays once)
 *         -> GAME_SELECTOR
 */
enum class Screen {
    INTRO,
    HOME,
    PLAY_INTRO,
    GAME_SELECTOR
}
