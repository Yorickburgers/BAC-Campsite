insert into professions (name, description, hp_modifier, primary_ability, trait_one, trait_two, trait_three, trait_four)
values  ('Barbarian', 'A Barbarian is a brutish melee attacker who uses their rage to empower their attacks with reckless abandon.', 12, 'Strength', 'Rage', 'Unarmored Defense (Constitution)', 'Reckless Attack', 'Great Weapon Master'),
        ('Bard', 'A Bard is a charismatic musician who uses the power of magic to support and inspire their allies and confuse and control enemies.', 8, 'Charisma', 'Vicious Mockery', 'Bardic Inspiration', 'Mantle of Majesty', 'Song of Rest'),
        ('Cleric', 'A Cleric is a devout individual who receives spellcasting powers via worship of a deity, which they use for devastating attacks and protective magics.', 8, 'Wisdom', 'Channel Divinity', 'Turn Undead', 'Blessed Strikes', 'Divine Intervention'),
        ('Druid', 'A Druid is a peaceful individual who lives in harmony with nature, being able to shape into animals to support their allies.', 8, 'Wisdom', 'Wild Shaping', 'Regrowth', 'Speak with Animals and Plants', 'Ageless'),
        ('Fighter', 'A Fighter is a trained attacker who has honed their skill in combat to deliver devastating attacks.', 10, 'Strength', 'Parry', 'Shield Master', 'Second Wind', 'Extra Attack'),
        ('Monk', 'A Monk is a disciplined martial artist who channels the power of their spirit into their fists to make lightning-fast strikes.', 8, 'Dexterity', 'Ki Points', 'Unarmored Defense (Wisdom)', 'Flurry of Blows', 'Deflect Attack'),
        ('Paladin', 'A Paladin is a lawful and devout knight who emboldens their resolve by binding themselves to an oath.', 10, 'Strength', 'Smite', 'Divine Shield', 'Mounted Combat', 'Penance'),
        ('Ranger', 'A Ranger is accustomed to living in the wilds, using their knowledge of nature to support allies and control the battlefield.', 10, 'Dexterity', 'Animal Companion', 'Trapping', 'Foraging', 'Snipe Shot'),
        ('Rogue', 'A Rogue is a thief who hides in the shadows and ambushes enemies to deal crippling sneak attacks.', 8, 'Dexterity', 'Stealth', 'Sneak Attack', 'Parlor Tricks', 'Tricks of the Trade'),
        ('Sorcerer', 'A Sorcerer has volatile magic coursing through their veins since birth or a rare occurrence.', 6, 'Charisma', 'Metamagic', 'Telepath', 'Magic Veins', 'Mystic Arcanum'),
        ('Warlock', 'A Warlock received versatile and often dark spellcasting powers through a pact they have made with a powerful patron.', 8, 'Charisma', 'Pact Magic', 'Eldritch Invocations', 'Mind Games', 'Ritualist'),
        ('Wizard', 'A Wizard has studied magic meticulously, having gained magical flexibility to overcome any challenge through a wide range of spells.', 6, 'Intelligence', 'Spell Book', 'Transcribe Scroll', 'Familiar', 'Recuperate');

insert into specializations (name, description, trait_one, trait_two, trait_three, trait_four, profession_id)
values  ('Path of the Totem', 'The Barbarian channels the bestial powers of a totem spirit, giving them bestial powers.', 'Pick Totem', 'Charge', 'Fury', 'Primal Savagery', 1),
        ('Path of the World Tree', 'The Barbarian channels the power of the world tree, giving them spectral battlefield manipulation.', 'Spectral Roots', 'Barkskin', 'Stasis Sap', 'Nourish', 1),
        ('Path of the Berserker', 'The Barbarian channels their rage, mitigating incoming damage.', 'Reckless Haze', 'Burning Up', 'Primal Knowledge', 'Cleave', 1),
        ('College of Valour', 'The Bard studied at the College of Valour, boosting melee capabilities.', 'Flourish', 'Riposte', 'Song of Battle', 'Inspire', 2),
        ('College of Eloquence', 'The Bard studied at the College of Eloquence, enhancing the power of their words.', 'Round', 'Verse', 'Song of Inspiration', 'Whisper', 2),
        ('College of Spirits', 'The Bard studied at the College of Spirits, granting them spiritual, mind-altering capabilities.', 'Speak with Dead', 'Otherworldly Grace', 'Suggestion', 'Spiritual Awakening', 2),
        ('Twilight Domain', 'The Cleric worships a deity of balance, resulting in strong support capabilities.', 'Twilight Sanctuary', 'Steps of Night', 'Vigilant Blessing', 'Twilight Shroud', 3),
        ('War Domain', 'The Cleric uses their divine powers for powerful battlefield capabilities.', 'War Priest', 'Guided Strike', 'War God''s Blessing', 'Avatar of Battle', 3),
        ('Life Domain', 'The Cleric uses their divine power for empowered healing and boons.', 'Disciple of Life', 'Blessed Healer', 'Preserve Life', 'Supreme Beacon', 3),
        ('Circle of Spores', 'The Circle of Spores honors the role of decay in the natural cycle of life, granting them persistent damaging buffs.', 'Symbiotic Entity', 'Fungal Infestation', 'Spreading Spores', 'Fungal Body', 4),
        ('Circle of the Moon', 'The Circle of the Moon honors the changing shape of the moon, empowering their Wild Shaping capabilities.', 'Circle Forms', 'Primal Strike', 'Elemental Wild Shapes', 'Thousand Forms', 4),
        ('Circle of Stars', 'The Circle of Stars honors the constellations in the night sky, granting them the power to Wild Shape into Starry Forms', 'Starry Form', 'Cosmic Omen', 'Twinkling Constellations', 'Full of Stars', 4),
        ('Battle Master', 'The Battle Master can employ a plethora of battle manoeuvres, allowing them stronger manipulation of their enemies.', 'Student of War', 'Know Your Enemy', 'Combat Superiority', 'Relentless', 5),
        ('Champion', 'The Champion ues a heightened insight in the anatomy of their targets to deliver devastating critical strikes.', 'Anatomy', 'Remarkable Athlete', 'Superior Critical', 'Survivor', 5),
        ('Eldritch Knight', 'The Eldritch Knight uses supportive magic, supplementing their weapon attacks with deadly magical effects.', 'Weapon Bond', 'War Magic', 'Eldritch Strike', 'Arcane Charge', 5),
        ('Way of the Open Hand', 'Following the Way of the Open Hand boosts unarmed capabilities.', 'Open Hand Technique', 'Wholeness of Body', 'Tranquility', 'Quivering Palm', 6),
        ('Way of Shadows', 'Following the Way of Shadows enables short-range teleportation into shadows and vision manipulation through magical darkness.', 'Shadow Arts', 'Shadow Step', 'Cloak of Shadows', 'Opportunist', 6),
        ('Way of Mercy', 'Following the Way of Mercy grants the monk powerful execution strikes.', 'Hand of Harm', 'Physician''s Touch', 'Flurry of Harm', 'Ultimate Mercy', 6),
        ('Oath of Conquest', 'Making an Oath of Conquest empowers the spell damage output of the Paladin.', 'Guided Strike', 'Aura of Conquest', 'Scornful Rebuke', 'Invincible Conqueror', 7),
        ('Oath of Devotion', 'Making an Oath of Devotion doubles down on the damage output against fiends, undead and abberations.', 'Sacred Weapon', 'Aura of Devotion', 'Purity of Spirit', 'Holy Nimbus', 7),
        ('Oathbreaker', 'Breaking an Oath results in a twisted morality, a command of the undead and a frightening presence to control enemies.', 'Dreadful Aspect', 'Aura of Hate', 'Control Undead', 'Dread Lord', 7),
        ('Drakewarden', 'A Drakewarden prefers taming dragonkin as their companion, granting the Ranger elemental powers.', 'Drake Mount', 'Bond of Fang and Scale', 'Drake''s Breath', 'Perfected Bond', 8),
        ('Gloom Stalker', 'A Gloom Stalker keeps to the shadows to deliver deadly precision strikes.', 'Dread Ambusher', 'Umbral Sight', 'Stalker''s Flurry', 'Shadowy Dodge', 8),
        ('Swarmkeeper', 'A Swarmkeeper specializes in large hordes of tiny creatures to pester their enemies into submission and defeat.', 'Gather the Swarm', 'Writhing Tide', 'Mighty Swarm', 'Swarming Dispersal', 8),
        ('Assassin', 'An Assassin delivers deadly precision strikes, often without ever being seen.', 'Assassination', 'Infiltration', 'Impostor', 'Death Strike', 9),
        ('Phantom', 'A Phantom is able to control visibility in battle, and employs mind-bending tactics to confuse enemies.', 'Whispers of the Dead', 'Tokens of the Departed', 'Ghost Walk', 'Death''s Friend', 9),
        ('Swashbuckler', 'A Swashbuckler has mastered underhanded tactics to incapacitate enemies and manipulate the rules of engagement.', 'Fancy Footwork', 'Rakish Audacity', 'Panache', 'Master Duelist', 9),
        ('Draconic Bloodline', 'A draconic ancestor is the source of strong elemental magics in this sorcerer.', 'Draconic Resilience', 'Elemental Affinity', 'Dragon Wings', 'Draconic Presence', 10),
        ('Aberrant Mind', 'From a young age, this Sorcerer has exhibited psionic powers, now translating into powerful manipulative spells.', 'Psionics', 'Psychic Defenses', 'Revelation in Flesh', 'Warping Implosion', 10),
        ('Clockwork Soul', 'An encounter with the Plane of Order has left this Sorcerer with powerful reality-warping abilities.', 'Restore Balance', 'Bastion of Law', 'Trance of Order', 'Clockwork Cavalcade', 10),
        ('The Undying', 'A pact with an Undying creature empowers the warlock with a command over the undead and a mastery over death (and avoiding it).', 'Among the Dead', 'Defy Death', 'Phylactery', 'Indestructable Life', 11),
        ('The Fiend', 'A pact with a demon in the Nine Hells has given the warlock strong elemental magic and power over planar banishment.', 'Dark One''s Blessing', 'Dark One''s Own Luck', 'Fiendish Resilience', 'Hurl through Hell', 11),
        ('The Great Old One', 'A pact with an eldritch outer god has given the warlock the ability to control the minds of others to instill fear or manipulate social interactions.', 'Awakened Mind', 'Entropic Ward', 'Thought Shield', 'Create Thrall', 11),
        ('School of Divination', 'The Wizard has studied Divination spells in particular, empowering the gift of Foresight to gain an advantage in future encounters', 'Divination Savant', 'Future Sight', 'Third Eye', 'Greater Portent', 12),
        ('School of Evocation', 'The Wizard has studied Evocation spells in particular, boosting their raw magical damage output and enabling them to avoid damaging their allies.', 'Evocation Savant', 'Sculpt Spells', 'Potent Cantrips', 'Overchannel', 12),
        ('School of Abjuration', 'The Wizard has studied Abjuration spells in particular, boosting their defensive spells and granting them the power to conjure powerful shields.', 'Abjuration Savant', 'Arcane Ward', 'Projected Ward', 'Spell Resistance', 12);

insert into users (username, password, email)
values ('admin', '$2a$04$4krYvHPkbhgygu8Ypmey/ud16dLRDLZp6GPGHdWBUlhbTuUukQnjC', 'admin@admin.nl'),
       ('testuser', '$2a$04$cJxtmFJf.70edAdSiYeSjeFhAnsEWD/tXjbnEN4Y9GhpPkLTquD52', 'user@test.nl');

insert into authorities (username, authority)
values ('admin', 'ROLE_ADMIN'),
       ('testuser', 'ROLE_USER');