#include "gadget.h"
#include <QSharedData>
#include <QHash>
#include <QDataStream>

class GadgetData : public QSharedData {
public:
    Gadget::GadgetType type;
    QString name;
    int price;
    Planet::TechLevel minimumTechLevel;
};

Gadget::Gadget(GadgetType type) : data(new GadgetData)
{
    data->type = type;

    if (type == Gadget::ExtraCargo) {
        data->name = "Extra Cargo";
        data->price = 100;
        data->minimumTechLevel = Planet::PreAgriculture;
    } else if (type == Gadget::EscapePod) {
        data->name = "Escape Pod";
        data->price = 200;
        data->minimumTechLevel = Planet::PreAgriculture;
    } else if (type == Gadget::NavigationSystem) {
        data->name = "Navigation System";
        data->price = 200;
        data->minimumTechLevel = Planet::Medieval;
    } else if (type == Gadget::AutoRepairSystem) {
        data->name = "Auto-Repair System";
        data->price = 300;
        data->minimumTechLevel = Planet::Medieval;
    } else if (type == Gadget::TargetingSystem) {
        data->name = "Targeting System";
        data->price = 300;
        data->minimumTechLevel = Planet::Renaissance;
    } else if (type == Gadget::CloakingDevice) {
        data->name = "Cloaking Device";
        data->price = 1000;
        data->minimumTechLevel = Planet::PostIndustrial;
    }
}

Gadget::Gadget(const Gadget &rhs) : data(rhs.data)
{
}

Gadget::GadgetType Gadget::getType() const {
    return data->type;
}

QString Gadget::getName() const {
    return data->name;
}

int Gadget::getPrice() const {
    return data->price;
}

Planet::TechLevel Gadget::getMinimumTechLevel() const {
    return data->minimumTechLevel;
}

Gadget &Gadget::operator=(const Gadget &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

bool operator==(const Gadget& gadget1, const Gadget& gadget2) {
    return gadget1.getType() == gadget2.getType();
}

uint qHash(const Gadget& gadget) {
    return qHash(gadget.getType());
}

QDataStream& operator<<(QDataStream& stream, const Gadget gadget) {
    stream << gadget.getType();

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Gadget& gadget) {
    Gadget::GadgetType type;

    stream >> type;

    gadget = Gadget(type);

    return stream;
}

QDataStream& operator<<(QDataStream& stream, const Gadget::GadgetType gadgetType) {
    stream << (int)gadgetType;

    return stream;
}

QDataStream& operator>>(QDataStream& stream, Gadget::GadgetType& gadgetType) {
    int gadgetTypeInt;

    stream >> gadgetTypeInt;

    gadgetType = static_cast<Gadget::GadgetType>(gadgetTypeInt);

    return stream;
}

Gadget::~Gadget()
{
}
