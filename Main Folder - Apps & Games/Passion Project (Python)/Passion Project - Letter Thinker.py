# Passion Project - Letter Thinker
# Raghav Ramesh

# Most of the pygame stuff was learned from pygame's documentation, especially the music and font.
# Background Music Source: Super Mario 3D Land - Asuka Hayazaki, Takeshi Hama, and Mahito Yokota
# Correct and Skip sounds Source: Undertale - Toby Fox

# -----Import and File References-----

# Import libraries
import random as rand
import pygame as game
import sys as system

# Text Files
letters = r"Letter Thinker Files\letters.txt"
animals = r"Letter Thinker Files\animals.txt"
restaurants = r"Letter Thinker Files\restauraunts.txt"
countries = r"Letter Thinker Files\countries.txt"
comp_sci_objects = r"Letter Thinker Files\compsciObjects.txt"

# -----Game Initialization-----

# Game Start
game.init()

# Game Sound
game.mixer.init()

# Audio Files
bg_music = r"Files\music.mp3"
correct_sound = game.mixer.Sound("Files\correct.wav")
wrong_sound = game.mixer.Sound("Files\wrong.wav")
skip_sound = game.mixer.Sound("Files\skip.wav")

# Music
game.mixer.music.load(bg_music)
game.mixer.music.set_volume(0.3)
game.mixer.music.play(-1, 2000)

# Screen
screen = game.display.set_mode((800, 600), game.RESIZABLE | game.SCALED)
screen_color = (250, 250, 124)
screen.fill(screen_color)
game.display.set_caption("Letter Thinker")

# Framerate
clock = game.time.Clock()

# Font
font = game.font.SysFont("Comic Sans", 30)

# -----Button Creation-----
class Button:
    def __init__(self, text, x, y, width, height, normal_color, hover_color, text_color):

        # Text and shape
        self.text = text
        self.rect = game.Rect(x, y, width, height)

        # Colors
        self.normal_color = normal_color
        self.hover_color = hover_color
        self.text_color = text_color
        
        # Font and visibility
        self.font = font
        self.visible = True

    # Display
    def draw(self, surface):

        if not self.visible:
            return

        # Find mouse position
        mouse_pos = game.mouse.get_pos()

        # Indicate mouse over button
        if self.rect.collidepoint(mouse_pos):
            color = self.hover_color
        else:
            color = self.normal_color
        
        # Button background
        game.draw.rect(surface, color, self.rect, border_radius=12)
        
        # Center the text inside the button rect
        text_surf = self.font.render(self.text, True, self.text_color)
        text_rect = text_surf.get_rect(center=self.rect.center)
        surface.blit(text_surf, text_rect)

    # Clickable
    def is_clicked(self, event):
        if not self.visible:
            return False
        # If left side of mouse clicked on button
        if ((event.type == game.MOUSEBUTTONDOWN) and (event.button == 1)):
            if self.rect.collidepoint(event.pos):
                return True
        return False

# -----Buttons-----

# Start button
start_button = Button(

    # Text
    text = "Start",

    # Position and size
    x = 300,
    y = 200,
    width = 200,
    height = 50,

    # Color
    normal_color = (50, 200, 100),
    hover_color = (0, 128, 0),
    text_color = (255, 255, 255)
)

# Quit button
quit_button = Button(

    # Text
    text = "Quit",

    # Position and size
    x = 300,
    y = 280,
    width = 200,
    height = 50,

    # Color
    normal_color = (195, 33, 72),
    hover_color = (145, 23, 54),
    text_color = (255, 255, 255)
)

# Skip button (Starts invisible)
skip_button = Button(

    # Text
    text = "Skip",

    # Position and size
    x = 0,
    y = 0,
    width = 120,
    height = 50,

    # Color
    normal_color = (0, 100, 200),
    hover_color = (0, 50, 100),
    text_color = (255, 255, 255)
)
skip_button.visible = False


# -----Load in Lists-----

with (open(letters, "r") as file):
    letter_list = [line.upper() for line in file.read().splitlines()]
rand.shuffle(letter_list) 

with (open(animals, "r") as file):
    animal_list = [line.upper() for line in file.read().splitlines()]
rand.shuffle(animal_list)

with (open(countries, "r") as file):
    countries_list = [line.upper() for line in file.read().splitlines()]
rand.shuffle(countries_list) 

with (open(comp_sci_objects, "r") as file):
    compSci_list = [line.upper() for line in file.read().splitlines()]
rand.shuffle(compSci_list)

with (open(restaurants, "r") as file):
    restaurant_list = [line.upper() for line in file.read().splitlines()]
rand.shuffle(restaurant_list)

# Set lists
categories = {
    "Animals": animal_list,
    "Countries": countries_list,
    "Computer Science": compSci_list,
    "Restaurants or Fast Foods": restaurant_list
}

# -----Gameplay Variables-----
game_started = False

# Guessing
current_category = ""
target_letter = ""
user_input = ""
feedback = ""

# Score / User Data
correct = 0
wrong = 0
skip = 0

# -----Generate Prompts-----
def prompt():
    global current_category, target_letter

    # Random category name
    current_category = rand.choice(list(categories.keys()))
    
    # Random word from that selected category
    chosen_category = categories[current_category]
    if chosen_category:
        random_word = rand.choice(chosen_category)
        # First letter from a real word so at least some word is available
        target_letter = random_word[0]
    else:
        # Emergency backup fallback if a file is entirely empty
        target_letter = rand.choice(letter_list)


# -----Game Loop-----
while True:

    # Prevent text overlap
    screen.fill(screen_color)

    # Keep track of player interaction
    for event in game.event.get():
        if event.type == game.QUIT:
            game.quit()
            system.exit()
        
        # All button clicks

        # Start Button Clicked
        if start_button.is_clicked(event):
            start_button.visible = False
            
            # Quit button display
            quit_button.rect.x = 630
            quit_button.rect.y = 530
            quit_button.rect.width = 150
            quit_button.rect.height = 50
        
            # Skip button display
            skip_button.rect.x = 490
            skip_button.rect.y = 530
            skip_button.visible = True
            
            # Game Set
            game_started = True
            prompt()
            user_input = ""
            feedback = ""
            
        # Quit Button Clicked
        if quit_button.is_clicked(event):
            game.quit()
            system.exit()
            
        # Skip Button Clicked
        if skip_button.is_clicked(event):
            # New prompt
            skip_sound.play()
            skip += 1
            current_category = rand.choice(list(categories.keys()))
            target_letter = rand.choice(letter_list).upper()
            user_input = ""
            feedback = "Skipped!"
        
        # Find user input for typing
        if game_started and event.type == game.KEYDOWN:
            if event.key == game.K_RETURN:
                user_input = user_input.upper()

                # Confirm user's answer to follow given letter and chosen category
                starts_with_letter = user_input.startswith(target_letter)
                is_in_category = user_input in categories[current_category]
                
                # User is correct
                if starts_with_letter and is_in_category:
                    correct_sound.play()
                    feedback = "Correct! " + user_input + " is an entry!"
                    correct += 1
                    
                    # Next Round
                    prompt()
                    user_input = ""
                
                # User is wrong
                else:
                    wrong_sound.play()
                    wrong += 1
                    if not starts_with_letter:
                        feedback = "Wrong! Must start with " + target_letter + "."
                    else:
                        feedback = "Wrong! Not found in " + current_category + "."
            elif event.key == game.K_BACKSPACE:
                user_input = user_input[:-1]
            else:
                user_input += event.unicode

    # -----Show Buttons-----
    start_button.draw(screen)
    quit_button.draw(screen)

    if (skip_button.visible):
        skip_button.draw(screen)

    # -----Show Text-----
    if game_started:
        # Display Score
        score_surf = font.render("Correct: " + str(correct) + "  |  Wrong: " + str(wrong) + "  |  Skips: " + str(skip), True, (100, 149, 237))
        screen.blit(score_surf, (50, 15))

        # Display Category
        category_check = font.render("Category: " + current_category, True, (0, 149, 237))
        screen.blit(category_check, (50, 70))
        
        # Display Given Target Letter
        letter_check = font.render("Must start with the letter: '" + target_letter + "'", True, (100, 215, 0))
        screen.blit(letter_check, (50, 120))
        
        # User Input Box
        input_label = font.render("Type an answer & press enter: ", True, (200, 0, 120))
        screen.blit(input_label, (50, 240))
        
        input_check = font.render(user_input.upper() + "|", True, (255, 0, 255))
        screen.blit(input_check, (50, 290))

        # Display Feedback
        if feedback:
            # Set color based on result
            if "Correct" in feedback:
                color = (0, 255, 100)
            elif "Skipped" in feedback:
                color = (0, 70, 200)
            else:
                color = (200, 50, 0)
            
            feedback_check = font.render(feedback, True, color)
            screen.blit(feedback_check, (50, 380))

    # Refresh Rate
    game.display.flip()
    clock.tick(60)