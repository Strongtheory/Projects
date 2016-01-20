#include "galaxy.h"
#include <QMap>
#include <QSharedData>
#include <QStringList>
#include <QTime>
#include <QtMath>

Galaxy Galaxy::instance = Galaxy();

class GalaxyData : public QSharedData {
public:
    QMap<QString, SolarSystem> solarSystems;
};

Galaxy::Galaxy() : data(new GalaxyData)
{
    qsrand(QTime::currentTime().msec());

    QStringList systemNames;
    systemNames.append(QStringLiteral("Earth616"));
    systemNames.append(QStringLiteral("Utopia"));
    systemNames.append(QStringLiteral("Omega"));
    systemNames.append(QStringLiteral("Tantalos"));
    systemNames.append(QStringLiteral("Carzon"));
    systemNames.append(QStringLiteral("Exo"));
    systemNames.append(QStringLiteral("Destiny"));
    systemNames.append(QStringLiteral("Frolix"));
    systemNames.append(QStringLiteral("Acamar"));
    systemNames.append(QStringLiteral("Adahn")); // The alternate personality for The Nameless One in "Planescape: Torment"
    systemNames.append(QStringLiteral("Aldea"));
    systemNames.append(QStringLiteral("Andevian"));
    systemNames.append(QStringLiteral("Antedi"));
    systemNames.append(QStringLiteral("Balosnee"));
    systemNames.append(QStringLiteral("Baratas"));
    systemNames.append(QStringLiteral("Brax")); // One of the heroes in Master of Magic
    systemNames.append(QStringLiteral("Bretel")); // This is a Dutch device for keeping your pants up.
    systemNames.append(QStringLiteral("Calondia"));
    systemNames.append(QStringLiteral("Campor"));
    systemNames.append(QStringLiteral("Capelle")); // The city I lived in while programming this game
    systemNames.append(QStringLiteral("Carzon"));
    systemNames.append(QStringLiteral("Castor")); // A Greek demi-god
    systemNames.append(QStringLiteral("Cestus"));
    systemNames.append(QStringLiteral("Cheron"));
    systemNames.append(QStringLiteral("Courteney")); // After Courteney Cox…
    systemNames.append(QStringLiteral("Daled"));
    systemNames.append(QStringLiteral("Damast"));
    systemNames.append(QStringLiteral("Davlos"));
    systemNames.append(QStringLiteral("Deneb"));
    systemNames.append(QStringLiteral("Deneva"));
    systemNames.append(QStringLiteral("Devidia"));
    systemNames.append(QStringLiteral("Draylon"));
    systemNames.append(QStringLiteral("Drema"));
    systemNames.append(QStringLiteral("Endor"));
    systemNames.append(QStringLiteral("Esmee")); // One of the witches in Pratchett's Discworld
    systemNames.append(QStringLiteral("Exo"));
    systemNames.append(QStringLiteral("Ferris")); // Iron
    systemNames.append(QStringLiteral("Festen")); // A great Scandinavian movie
    systemNames.append(QStringLiteral("Fourmi")); // An ant, in French
    systemNames.append(QStringLiteral("Frolix")); // A solar system in one of Philip K. Dick's novels
    systemNames.append(QStringLiteral("Gemulon"));
    systemNames.append(QStringLiteral("Guinifer")); // One way of writing the name of king Arthur's wife
    systemNames.append(QStringLiteral("Hades")); // The underworld
    systemNames.append(QStringLiteral("Hamlet")); // From Shakespeare
    systemNames.append(QStringLiteral("Helena")); // Of Troy
    systemNames.append(QStringLiteral("Hulst")); // A Dutch plant
    systemNames.append(QStringLiteral("Iodine")); // An element
    systemNames.append(QStringLiteral("Iralius"));
    systemNames.append(QStringLiteral("Janus")); // A seldom encountered Dutch boy's name
    systemNames.append(QStringLiteral("Japori"));
    systemNames.append(QStringLiteral("Jarada"));
    systemNames.append(QStringLiteral("Jason")); // A Greek hero
    systemNames.append(QStringLiteral("Kaylon"));
    systemNames.append(QStringLiteral("Khefka"));
    systemNames.append(QStringLiteral("Kira")); // My dog's name
    systemNames.append(QStringLiteral("Klaatu")); // From a classic SF movie
    systemNames.append(QStringLiteral("Klaestron"));
    systemNames.append(QStringLiteral("Korma")); // An Indian sauce
    systemNames.append(QStringLiteral("Kravat")); // Interesting spelling of the French word for "tie"
    systemNames.append(QStringLiteral("Krios"));
    systemNames.append(QStringLiteral("Laertes")); // A king in a Greek tragedy
    systemNames.append(QStringLiteral("Largo"));
    systemNames.append(QStringLiteral("Lave")); // The starting system in Elite
    systemNames.append(QStringLiteral("Ligon"));
    systemNames.append(QStringLiteral("Lowry")); // The name of the "hero" in Terry Gilliam's "Brazil"
    systemNames.append(QStringLiteral("Magrat")); // The second of the witches in Pratchett's Discworld
    systemNames.append(QStringLiteral("Malcoria"));
    systemNames.append(QStringLiteral("Melina"));
    systemNames.append(QStringLiteral("Mentar")); // The Psilon home system in Master of Orion
    systemNames.append(QStringLiteral("Merik"));
    systemNames.append(QStringLiteral("Mintaka"));
    systemNames.append(QStringLiteral("Montor")); // A city in Ultima III and Ultima VII part 2
    systemNames.append(QStringLiteral("Mordan"));
    systemNames.append(QStringLiteral("Myrthe")); // The name of my daughter
    systemNames.append(QStringLiteral("Nelvana"));
    systemNames.append(QStringLiteral("Nix")); // An interesting spelling of a word meaning "nothing" in Dutch
    systemNames.append(QStringLiteral("Nyle")); // An interesting spelling of the great river
    systemNames.append(QStringLiteral("Odet"));
    systemNames.append(QStringLiteral("Og")); // The last of the witches in Pratchett's Discworld
    systemNames.append(QStringLiteral("Omega")); // The end of it all
    systemNames.append(QStringLiteral("Omphalos")); // Greek for navel
    systemNames.append(QStringLiteral("Orias"));
    systemNames.append(QStringLiteral("Othello")); // From Shakespeare
    systemNames.append(QStringLiteral("Parade")); // This word means the same in Dutch and in English
    systemNames.append(QStringLiteral("Penthara"));
    systemNames.append(QStringLiteral("Picard")); // The enigmatic captain from ST:TNG
    systemNames.append(QStringLiteral("Pollux")); // Brother of Castor
    systemNames.append(QStringLiteral("Quator"));
    systemNames.append(QStringLiteral("Rakhar"));
    systemNames.append(QStringLiteral("Ran")); // A film by Akira Kurosawa
    systemNames.append(QStringLiteral("Regulas"));
    systemNames.append(QStringLiteral("Relva"));
    systemNames.append(QStringLiteral("Rhymus"));
    systemNames.append(QStringLiteral("Rochani"));
    systemNames.append(QStringLiteral("Rubicum")); // The river Ceasar crossed to get into Rome
    systemNames.append(QStringLiteral("Rutia"));
    systemNames.append(QStringLiteral("Sarpeidon"));
    systemNames.append(QStringLiteral("Sefalla"));
    systemNames.append(QStringLiteral("Seltrice"));
    systemNames.append(QStringLiteral("Sigma"));
    systemNames.append(QStringLiteral("Sol")); // That's our own solar system
    systemNames.append(QStringLiteral("Somari"));
    systemNames.append(QStringLiteral("Stakoron"));
    systemNames.append(QStringLiteral("Styris"));
    systemNames.append(QStringLiteral("Talani"));
    systemNames.append(QStringLiteral("Tamus"));
    systemNames.append(QStringLiteral("Tantalos")); // A king from a Greek tragedy
    systemNames.append(QStringLiteral("Tanuga"));
    systemNames.append(QStringLiteral("Tarchannen"));
    systemNames.append(QStringLiteral("Terosa"));
    systemNames.append(QStringLiteral("Thera")); // A seldom encountered Dutch girl's name
    systemNames.append(QStringLiteral("Titan")); // The largest moon of Jupiter
    systemNames.append(QStringLiteral("Torin")); // A hero from Master of Magic
    systemNames.append(QStringLiteral("Triacus"));
    systemNames.append(QStringLiteral("Turkana"));
    systemNames.append(QStringLiteral("Tyrus"));
    systemNames.append(QStringLiteral("Umberlee")); // A god from AD&D, which has a prominent role in Baldur's Gate
    systemNames.append(QStringLiteral("Utopia")); // The ultimate goal
    systemNames.append(QStringLiteral("Vadera"));
    systemNames.append(QStringLiteral("Vagra"));
    systemNames.append(QStringLiteral("Vandor"));
    systemNames.append(QStringLiteral("Ventax"));
    systemNames.append(QStringLiteral("Xenon"));
    systemNames.append(QStringLiteral("Xerxes")); // A Greek hero
    systemNames.append(QStringLiteral("Yew")); // A city which is in almost all of the Ultima games
    systemNames.append(QStringLiteral("Yojimbo")); // A film by Akira Kurosawa
    systemNames.append(QStringLiteral("Zalkon"));
    systemNames.append(QStringLiteral("Zuul"));

    for (int i = 0; i < systemNames.size(); i++) {
        QString name = systemNames.at(i);
        SolarSystem solarSystem(name);
        while (collidesWithAnotherSystem(solarSystem)) {
            solarSystem = SolarSystem(name);
        }
        data->solarSystems.insert(name, solarSystem);
    }
}

Galaxy::Galaxy(const Galaxy &rhs) :
    QObject(),
    data(rhs.data)
{
}

const QList<SolarSystem> Galaxy::getSolarSystems() const {
    return data->solarSystems.values();
}

const SolarSystem Galaxy::getSolarSystem(QString name) const {
    return data->solarSystems.value(name);
}

double Galaxy::getDistanceBetweenSolarSystems(const SolarSystem origin, const SolarSystem destination) const {
    return qSqrt(qPow(destination.getX() - origin.getX(), 2) + qPow(destination.getY() - origin.getY(), 2));
}

Galaxy Galaxy::getInstance() {
    return instance;
}

void Galaxy::setSolarSystems(QList<SolarSystem> solarSystems) {
    data->solarSystems.clear();
    for (int i = 0; i < solarSystems.size(); i++) {
        SolarSystem solarSystem = solarSystems.at(i);
        data->solarSystems.insert(solarSystem.getName(), solarSystem);
    }
}

Galaxy &Galaxy::operator=(const Galaxy &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

bool Galaxy::collidesWithAnotherSystem(SolarSystem system) const {
    for (int i = 0; i < data->solarSystems.values().length(); i++) {
        const SolarSystem existingSystem = data->solarSystems.values().at(i);
        if (!(existingSystem.getX() + 32 < system.getX() || existingSystem.getX() > system.getX() + 32
              || existingSystem.getY() + 32 < system.getY() || existingSystem.getY() > system.getY() + 32)) {
            return true;
        }
    }
    return false;
}

Galaxy::~Galaxy()
{
}

QDataStream& operator<<(QDataStream& stream, const Galaxy galaxy) {
    stream << galaxy.getSolarSystems();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Galaxy galaxy) {
    QList<SolarSystem> solarSystems;

    stream >> solarSystems;

    galaxy.setSolarSystems(solarSystems);

    return stream;
}
