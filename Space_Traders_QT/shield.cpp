#include "shield.h"
#include <QSharedData>
#include <QHash>
#include <QDataStream>

class ShieldData : public QSharedData {
public:
    Shield::ShieldType type;
    QString name;
    int price;
    int strength;
    bool reflective;
};

Shield::Shield(ShieldType type) : data(new ShieldData)
{
    data->type = type;

    if (type == Shield::CivilianEnergy) {
        data->name = "Civilian Energy";
        data->price = 100;
        data->strength = 100;
        data->reflective = false;
    } else if (type == Shield::MilitiaEnergy) {
        data->name = "Militia Energy";
        data->price = 200;
        data->strength = 200;
        data->reflective = false;
    } else if (type == Shield::MilitaryEnergy) {
        data->name = "Military Energy";
        data->price = 300;
        data->strength = 400;
        data->reflective = false;
    } else if (type == Shield::CivilianReflective) {
        data->name = "Civilian Reflective";
        data->price = 400;
        data->strength = 600;
        data->reflective = true;
    } else if (type == Shield::MilitiaReflective) {
        data->name = "Militia Reflective";
        data->price = 500;
        data->strength = 800;
        data->reflective = true;
    } else if (type == Shield::MilitaryReflective) {
        data->name = "Military Reflective";
        data->price = 600;
        data->strength = 1000;
        data->reflective = true;
    }
}

Shield::Shield(const Shield &rhs) : data(rhs.data)
{
}

Shield::ShieldType Shield::getType() const {
    return data->type;
}

QString Shield::getName() const {
    return data->name;
}

int Shield::getPrice() const {
    return data->price;
}

int Shield::getStrength() const {
    return data->strength;
}

bool Shield::isReflective() const {
    return data->reflective;
}

Shield &Shield::operator=(const Shield &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

bool operator==(const Shield& shield1, const Shield& shield2) {
    return shield1.getType() == shield2.getType();
}

uint qHash(const Shield& shield) {
    return qHash(shield.getType());
}

QDataStream& operator<<(QDataStream& stream, const Shield shield) {
    stream << shield.getType();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Shield& shield) {
    Shield::ShieldType shieldType;

    stream >> shieldType;

    shield = Shield(shieldType);

    return stream;
}

QDataStream& operator<<(QDataStream& stream, const Shield::ShieldType shieldType) {
    stream << (int)shieldType;

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Shield::ShieldType& shieldType) {
    int shieldTypeInt;

    stream >> shieldTypeInt;

    shieldType = static_cast<Shield::ShieldType>(shieldTypeInt);

    return stream;
}

Shield::~Shield()
{
}
